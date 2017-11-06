package com.example.charlie.battleship

import android.content.Context
import android.graphics.Color
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import javax.microedition.khronos.opengles.GL


class LoadedGameListAdapter(context: Context) : BaseAdapter() {

    private val mContext = context

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val textView = TextView(mContext)
        val game = GlobalData.gameObjectsData[position]
        if (game.status == "PLAYING") {
            textView.setText(game.status + "\n" +
                    "Player1 Ships Remaining: " + (5-game.player1SunkShips.size).toString() + "\n" +
                    "Player2 Ships Remaining: " + (5-game.player2SunkShips.size).toString() + "\n")
        }
        else {
            when(game.player1Wins) {
                true -> textView.setText("Player 1 Won the Game\n")
                false -> textView.setText("Player 2 Won the Game\n")
            }
        }
        textView.setTextColor(Color.WHITE)
        return textView
    }

    override fun getItem(position: Int): Any {
        return "TEST"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return GlobalData.gameObjectsData.size
    }

}