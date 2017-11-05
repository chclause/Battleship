package com.example.charlie.battleship

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import kotlinx.android.synthetic.main.activity_player.*
import android.widget.Toast
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView



class PlayerActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val gameID = intent.getIntExtra("GAME", 0)
        setContentView(R.layout.activity_player)
        val player1GridView : GridView

        player1GridView = this.findViewById(R.id.player1Grid)
        player1GridView.adapter = GridAdapter(this, gameID)

        player1GridView.onItemClickListener = object : OnItemClickListener {
           override fun onItemClick(parent: AdapterView<*>, v: View,
                            position: Int, id: Long) {
                if (GlobalData.player1Turn) {
                    GlobalData.gameObjectsData[gameID].player1Tiles[position].beenHit = true
                    player1GridView.invalidateViews()
                }
               else {
                    GlobalData.gameObjectsData[gameID].player1Tiles[position].beenHit = true
                    player1GridView.invalidateViews()
                }
                Toast.makeText(this@PlayerActivity, "$position",
                        Toast.LENGTH_SHORT).show()
               val intent = Intent(applicationContext, PlayerActivity::class.java)
               intent.putExtra("GAME", gameID)
               startActivity(intent)
            }
        }
    }
}
