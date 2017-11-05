package com.example.charlie.battleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pass.*

class PassActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pass)
        val gameID = intent.getIntExtra("GAME", 0)

        if (GlobalData.player1Turn) {
            playerPassScreenText.setText("Pass to Player 2")
        }
        else {
            playerPassScreenText.setText("Pass to Player 1")
        }

        playerPassButton.setOnClickListener {
            GlobalData.player1Turn = !GlobalData.player1Turn
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.putExtra("GAME", gameID)
            startActivity(intent)
        }
    }
}
