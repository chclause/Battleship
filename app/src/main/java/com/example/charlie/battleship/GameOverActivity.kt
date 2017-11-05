package com.example.charlie.battleship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val player1Wins = intent.getBooleanExtra("WON", true)
    }
}
