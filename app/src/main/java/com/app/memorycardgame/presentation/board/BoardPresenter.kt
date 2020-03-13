package com.app.memorycardgame.presentation.board

import com.app.memorycardgame.entity.MemoryCard
import com.app.memorycardgame.ui.board.IBoard
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject
import kotlin.random.Random

class BoardPresenter @Inject constructor(val router: Router) : MvpPresenter<IBoard>() {

    fun onBack() {
        router.exit()
    }

    fun generateRandomCards(): List<MemoryCard> {
        val cardCount = 8
        val cardList = MutableList(cardCount) { index -> MemoryCard(index) }
        val used = BooleanArray(cardCount)
        for (i in 1..(cardCount / 2)) {

            var firstCardPosition = Random.nextInt(0, cardCount)
            while (used[firstCardPosition]) firstCardPosition = Random.nextInt(0, cardCount)
            used[firstCardPosition] = true

            var secondCardPosition = Random.nextInt(0, cardCount)
            while (used[secondCardPosition]) secondCardPosition = Random.nextInt(0, cardCount)
            used[secondCardPosition] = true

            cardList[firstCardPosition] = MemoryCard(i)
            cardList[secondCardPosition] = cardList[firstCardPosition]
        }
        return cardList
    }
}