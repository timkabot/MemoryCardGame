package com.app.memorycardgame.presentation.mainMenu

import com.arellomobile.mvp.MvpView

interface IMainMenuView : MvpView {
    fun onPlayButtonClicked()
    fun onScoreboardButtonClicked()
}