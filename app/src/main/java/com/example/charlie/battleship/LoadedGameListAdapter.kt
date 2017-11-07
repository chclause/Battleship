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
        // Change the order so newest is at the top
        val game = GlobalData.gameObjectsData[GlobalData.gameObjectsData.size - position - 1]
        var statusText = game.status
        if (position == 0) {
            statusText = statusText + " - Newest Game"
        }
        if (game.status == "STARTED") {
            for (tile in game.player1Tiles) {
                if (tile.beenHit) {
                    statusText = "IN PROGRESS"
                    game.status = "IN PROGRESS"
                    break
                }
            }
        }
        // Geez this is hard to look upon, fix this later
        if (game.status == "STARTED" || game.status == "IN PROGRESS") {
            textView.setText(statusText + "\n" +
                    "Player1 Ships Remaining: " + (5-game.player1SunkShips.size).toString() + "\n" +
                    "Player2 Ships Remaining: " + (5-game.player2SunkShips.size).toString() + "\n")
        }
        else {
            when(game.player1Wins) {
                true -> textView.setText("COMPLETE\n" + "Player 1 Won the Game\n")
                false -> textView.setText("COMPLETE\n" + "Player 2 Won the Game\n")
            }
        }
        textView.setTextColor(Color.WHITE)
        return textView
    }

    override fun getItem(position: Int): Any {
        return ""
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return GlobalData.size()
    }

}