package com.app.memorycardgame.ui.global.list

import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.memorycardgame.R
import com.app.memorycardgame.Screens
import com.app.memorycardgame.entity.MemoryCard
import com.app.memorycardgame.model.data.storage.Prefs
import com.app.memorycardgame.utils.Utils.getDrawableByName
import com.app.memorycardgame.utils.delayMs
import com.app.memorycardgame.utils.scores_key
import com.app.memorycardgame.utils.toast
import com.app.memorycardgame.utils.vibrate
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.card_memory.view.*
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class BoardListAdapter @Inject constructor(
    private val router: Router,
    private val prefs: Prefs
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listOfBoards = listOf<MemoryCard>()
    var openedCards = mutableListOf<Int>()
    var tries = 0
    var points = 0
    lateinit var nickname: String
    var columnCount: Int = 2
    override fun getItemCount(): Int = listOfBoards.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val boardListViewHolder = viewHolder as BoardViewHolder
        boardListViewHolder.bind(listOfBoards[position])
        boardListViewHolder.closeCard()
    }

    fun setBoardsList(listOfBoards: List<MemoryCard>) {
        this.listOfBoards = listOfBoards
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val myCard =
            LayoutInflater.from(parent.context).inflate(R.layout.card_memory, parent, false)
        val metrics = myCard.context.resources.displayMetrics
        val height = metrics.heightPixels / (itemCount / columnCount)
        val width = metrics.widthPixels / columnCount
        val layoutParams = myCard.layoutParams
        layoutParams.height = height
        layoutParams.width = width
        myCard.layoutParams = layoutParams
        return BoardViewHolder(myCard)
    }


    inner class BoardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private fun closeOpenedCards() {
            closeCard()
            for (i in openedCards) notifyItemChanged(i)
            openedCards.clear()
        }

        private fun closeCards() {
            Handler().postDelayed({
                closeOpenedCards()
            }, delayMs)

        }

        private fun openCard(drawable: Drawable) {
            Glide.with(itemView.context).load(drawable).into(itemView.cardImage)
            listOfBoards[layoutPosition].closed = false
            openedCards.add(layoutPosition)
        }

        private fun increasePoints() {
            points += 2
            if (points == itemCount) {
                router.navigateTo(Screens.MainMenuScreen)
                saveResults()
                itemView.context.toast("You got $tries points")
            }
        }

        private fun saveResults() {
            val scores: ArrayList<String>? = prefs.getArrayList(scores_key) as ArrayList<String>?
            if (scores == null) prefs.saveArrayList(listOf("$nickname $tries"), scores_key)
            else {
                scores.add("$nickname $tries")
                prefs.saveArrayList(scores, scores_key)
            }
        }

        fun bind(memoryCard: MemoryCard) {
            val id = memoryCard.category
            val drawable = getDrawableByName("card$id", itemView.context)
            closeCard()
            itemView.setOnClickListener {
                if (listOfBoards[layoutPosition].closed) {
                    tries++
                    openCard(drawable)

                    if (openedCards.size == 2) {
                        closeCards()
                        itemView.context.vibrate()
                    }
                } else if (openedCards.size == 1 && openedCards[0] != layoutPosition) {
                    Glide.with(itemView.context).load(drawable).into(itemView.cardImage)
                    openedCards.clear()

                    increasePoints()
                }
            }
        }

        fun closeCard() {
            listOfBoards[layoutPosition].closed = true
            Glide.with(itemView.context).load(R.drawable.closed_card).into(itemView.cardImage)
        }

    }

}