package com.app.memorycardgame.ui.scoreBoard

import com.arellomobile.mvp.MvpView

interface IScoreBoardView : MvpView {
    fun initRecycler()
    fun setDate(data : List<String>)
}