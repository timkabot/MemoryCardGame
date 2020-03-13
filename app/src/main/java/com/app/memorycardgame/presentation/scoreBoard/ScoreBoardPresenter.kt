package com.app.memorycardgame.presentation.scoreBoard

import com.app.memorycardgame.model.data.storage.Prefs
import com.app.memorycardgame.ui.scoreBoard.IScoreBoardView
import com.app.memorycardgame.utils.scores_key
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ScoreBoardPresenter @Inject constructor(
    private val router: Router,
    private val prefs: Prefs
) : MvpPresenter<IScoreBoardView>() {
    private fun getScoreData(): List<String> {
        val scores: ArrayList<String>? =
            prefs.getArrayList(scores_key) as ArrayList<String>?
        if (scores != null) return scores
        return listOf("No scores available")
    }

    fun onBack() {
        router.exit()
    }

    fun updateData(){
        viewState.setDate(getScoreData())
    }
}