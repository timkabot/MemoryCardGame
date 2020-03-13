package com.app.memorycardgame.ui.global


import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.memorycardgame.di.DI
import com.app.memorycardgame.utils.objectScopeName
import com.arellomobile.mvp.MvpAppCompatFragment
import timber.log.Timber
import toothpick.Scope
import toothpick.Toothpick

private const val STATE_SCOPE_NAME = "state_scope_name"


abstract class BaseFragment : MvpAppCompatFragment() {
    abstract val layoutRes: Int

    private var instanceStateSaved: Boolean = false

    private val viewHandler = Handler()

    protected open val parentScopeName: String by lazy {
        (parentFragment as? BaseFragment)?.fragmentScopeName
            ?: DI.APP_SCOPE
    }

    private lateinit var fragmentScopeName: String
    protected lateinit var scope: Scope
        private set

    protected open fun installModules(scope: Scope) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentScopeName = savedInstanceState?.getString(STATE_SCOPE_NAME) ?: objectScopeName()

        Timber.d(fragmentScopeName, parentScopeName)

        if (Toothpick.isScopeOpen(fragmentScopeName)) {
            Timber.d("Get exist UI scope: $fragmentScopeName")
            println("Get exist UI scope: $fragmentScopeName")
            scope = Toothpick.openScope(fragmentScopeName)
        } else {
            Timber.d("Init new UI scope: $fragmentScopeName -> $parentScopeName")
            println("Init new UI scope: $fragmentScopeName -> $parentScopeName")
            scope = Toothpick.openScopes(parentScopeName, fragmentScopeName)
            installModules(scope)
        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(layoutRes, container, false)

    override fun onResume() {
        super.onResume()
        instanceStateSaved = false
    }

    // Fix for async views (like swipeToRefresh and RecyclerView)
    // If synchronously call actions on swipeToRefresh in sequence show and hide then swipeToRefresh will not hidden
    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        instanceStateSaved = true
        outState.putString(STATE_SCOPE_NAME, fragmentScopeName)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (needCloseScope()) {
            // Destroy this fragment with scope
            Timber.d("Destroy UI scope: $fragmentScopeName")
            Toothpick.closeScope(scope.name)
        }
    }

    // This is android, baby!
    private fun isRealRemoving(): Boolean =
        (isRemoving && !instanceStateSaved) || // Because isRemoving == true for fragment in backstack on screen rotation
                ((parentFragment as? BaseFragment)?.isRealRemoving() ?: false)

    // It will be valid only for 'onDestroy()' method
    private fun needCloseScope(): Boolean =
        when {
            activity?.isChangingConfigurations == true -> false
            activity?.isFinishing == true -> true
            else -> isRealRemoving()
        }


    open fun onBackPressed() {}
}