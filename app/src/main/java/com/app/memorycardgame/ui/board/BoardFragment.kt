package com.app.memorycardgame.ui.board

import android.content.res.Configuration
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.GridLayoutManager
import com.app.memorycardgame.R
import com.app.memorycardgame.model.data.storage.Prefs
import com.app.memorycardgame.presentation.board.BoardPresenter
import com.app.memorycardgame.ui.global.BaseFragment
import com.app.memorycardgame.ui.global.list.BoardListAdapter
import com.app.memorycardgame.utils.LIST_STATE_KEY
import com.app.memorycardgame.utils.argument
import com.app.memorycardgame.utils.nickname_key
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_board.*
import javax.inject.Inject


class BoardFragment(): BaseFragment(), IBoard {
    override val layoutRes = R.layout.fragment_board
    private lateinit var gridLayoutManager: GridLayoutManager
    private lateinit var boardListAdapter: BoardListAdapter
    var listState: Parcelable? = null
    private var columnCount = 2

    private var nickname by argument<String>(nickname_key, null)

    @InjectPresenter
    lateinit var presenter: BoardPresenter

    @ProvidePresenter
    fun providePresenter(): BoardPresenter =
        scope.getInstance(BoardPresenter::class.java)

    override fun onStart() {
        super.onStart()
        val prefs = Prefs(context!!)
        boardListAdapter = BoardListAdapter(presenter.router, prefs)

        initGridSize()
        initCards()

    }

    private fun checkOrientation(configuration: Configuration) {

        when(configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                columnCount = 4
                boardListAdapter.columnCount = 4
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                columnCount = 2
                boardListAdapter.columnCount = 4
            }
        }
    }

    private fun initGridSize() {
        checkOrientation(context!!.resources.configuration)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        checkOrientation(newConfig)
    }

    private fun initRecycler() {
        gridLayoutManager = GridLayoutManager(context, columnCount)
        boardRecycler.layoutManager = gridLayoutManager
        initAdapter()
    }

    private fun initAdapter(){
        boardListAdapter.nickname = nickname
        boardListAdapter.columnCount = columnCount
        boardRecycler.adapter = boardListAdapter

    }

    private fun updateRecycler() {
        boardListAdapter.setBoardsList(presenter.generateRandomCards())
    }

    override fun onBackPressed() {
        presenter.onBack()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = gridLayoutManager.onSaveInstanceState()
        outState.putParcelable(LIST_STATE_KEY, listState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let {
            listState = it.getParcelable(LIST_STATE_KEY)
        }
    }

    override fun onResume() {
        super.onResume()
        listState?.let {
            gridLayoutManager.onRestoreInstanceState(it)
        }
    }


    override fun initCards() {
        initRecycler()
        updateRecycler()
    }

    companion object {
        fun create(nickname: String) =
            BoardFragment().apply {
                arguments = Bundle().apply {
                    putString(nickname_key, nickname)
                }
            }
    }
}