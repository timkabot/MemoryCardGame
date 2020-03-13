package com.app.memorycardgame.presentation.scoreBoard

import android.content.Context
import com.app.memorycardgame.utils.Utils
import com.app.memorycardgame.utils.scores_key
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class ScoreBoardPresenter @Inject constructor(
    private val router: Router,
    private val context: Context
) : MvpPresenter<IScoreBoardView>() {
    fun getScoreData(): List<String> {
        val scores: ArrayList<String>? =
            Utils.getArrayList(context, scores_key) as ArrayList<String>?
        if (scores != null) {
            return scores
        }
        return listOf("No scores available")
    }

    fun onBack() {
        router.exit()
    }
}