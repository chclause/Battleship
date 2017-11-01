package com.example.charlie.battleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerActivityButton.setOnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            startActivity(intent)
        }
    }
}
