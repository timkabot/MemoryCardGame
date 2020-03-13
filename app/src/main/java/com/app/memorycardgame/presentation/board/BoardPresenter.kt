package com.app.memorycardgame.presentation.board

import com.app.memorycardgame.entity.MemoryCard
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

            var pos1 = Random.nextInt(0, cardCount)
            while (used[pos1]) pos1 = Random.nextInt(0, cardCount)
            used[pos1] = true

            var pos2 = Random.nextInt(0, cardCount)
            while (used[pos2]) pos2 = Random.nextInt(0, cardCount)
            used[pos2] = true

            cardList[pos1] = MemoryCard(i)
            cardList[pos2] = cardList[pos1]
        }
        return cardList
    }
}