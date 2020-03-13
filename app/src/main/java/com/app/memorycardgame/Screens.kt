package com.app.memorycardgame

import com.app.memorycardgame.ui.board.BoardFragment
import com.app.memorycardgame.ui.mainMenu.MainMenuFragment
import com.app.memorycardgame.ui.scoreBoard.ScoreBoardFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object MainMenuScreen : SupportAppScreen() {
        override fun getFragment() = MainMenuFragment()
    }

    data class BoardScreen(private val nickname: String) : SupportAppScreen() {
        override fun getFragment() = BoardFragment.create(nickname)
    }

    object ScoreBoardScreen : SupportAppScreen() {
        override fun getFragment() = ScoreBoardFragment()

    }

}