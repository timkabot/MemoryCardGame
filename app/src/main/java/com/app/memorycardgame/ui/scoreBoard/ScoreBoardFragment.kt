package com.app.memorycardgame.ui.scoreBoard

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.memorycardgame.R
import com.app.memorycardgame.presentation.scoreBoard.IScoreBoardView
import com.app.memorycardgame.presentation.scoreBoard.ScoreBoardPresenter
import com.app.memorycardgame.ui.global.BaseFragment
import com.app.memorycardgame.ui.global.list.ScoreboardAdapter
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_scoreboard.*

class ScoreBoardFragment : BaseFragment(), IScoreBoardView {
    override val layoutRes = R.layout.fragment_scoreboard
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var scoreboardAdapter: ScoreboardAdapter

    @InjectPresenter
    lateinit var presenter: ScoreBoardPresenter

    @ProvidePresenter
    fun providePresenter(): ScoreBoardPresenter =
        scope.getInstance(ScoreBoardPresenter::class.java)

    override fun onStart() {
        super.onStart()
        initRecycler()
        val data = presenter.getScoreData()
        updateRecycler(data)
    }

    override fun initRecycler() {
        linearLayoutManager = LinearLayoutManager(context)
        scoreboardRecycler.layoutManager = linearLayoutManager
        scoreboardAdapter = ScoreboardAdapter()
        scoreboardRecycler.adapter = scoreboardAdapter
    }

    private fun updateRecycler(data: List<String>) {
        scoreboardAdapter.setScoreBoardsList(data)

    }

    override fun onBackPressed() {
        presenter.onBack()
    }
}