package com.app.memorycardgame.presentation.mainMenu

import com.app.memorycardgame.Screens
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainMenuPresenter @Inject constructor(private val router: Router) :
    MvpPresenter<IMainMenuView>() {
    fun goToBoardScreen(nickname: String) {
        router.navigateTo(Screens.BoardScreen(nickname))
    }

    fun goToScoreboardScreen() {
        router.navigateTo(Screens.ScoreBoardScreen)
    }

    fun onBack() {
        router.exit()
    }
}