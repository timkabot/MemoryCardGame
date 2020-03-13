package com.app.memorycardgame.ui.global.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.memorycardgame.R
import kotlinx.android.synthetic.main.card_score.view.*

class ScoreboardAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listOfScores = listOf<String>()

    override fun getItemCount(): Int = listOfScores.size

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val scoreViewHolder = viewHolder as ScoreViewHolder
        scoreViewHolder.bind(listOfScores[position])
    }

    fun setScoreBoardsList(listOfScores: List<String>) {
        this.listOfScores = listOfScores
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val myCard =
            LayoutInflater.from(parent.context).inflate(R.layout.card_score, parent, false)
        return ScoreViewHolder(myCard)
    }


    inner class ScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(score: String) {
            itemView.scoreText.text = score
        }

    }

}