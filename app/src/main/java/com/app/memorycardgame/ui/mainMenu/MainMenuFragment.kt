package com.app.memorycardgame.ui.mainMenu

import android.os.Bundle
import com.app.memorycardgame.R
import com.app.memorycardgame.presentation.mainMenu.MainMenuPresenter
import com.app.memorycardgame.ui.global.BaseFragment
import com.app.memorycardgame.utils.toast
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_main_menu.*

class MainMenuFragment : BaseFragment(),
    IMainMenuView {
    override val layoutRes = R.layout.fragment_main_menu

    @InjectPresenter
    lateinit var presenter: MainMenuPresenter

    @ProvidePresenter
    fun providePresenter(): MainMenuPresenter =
        scope.getInstance(MainMenuPresenter::class.java)

    private fun initListeners() {
        startGameButton.setOnClickListener {
            onPlayButtonClicked()
        }
        leaderBoardButton.setOnClickListener {
            onScoreboardButtonClicked()
        }
    }

    override fun onPlayButtonClicked() {
        nickname_text_view.text?.let {
            if (it.isNotEmpty()) presenter.goToBoardScreen(it.toString())
            else context?.toast(getString(R.string.enter_nickname))
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListeners()

    }

    override fun onScoreboardButtonClicked() {
        presenter.goToScoreboardScreen()
    }

    override fun onBackPressed() {
        presenter.onBack()
    }
}