package com.app.memorycardgame.ui.mainMenu

import com.arellomobile.mvp.MvpView

interface IMainMenuView : MvpView {
    fun onPlayButtonClicked()
    fun onScoreboardButtonClicked()
}