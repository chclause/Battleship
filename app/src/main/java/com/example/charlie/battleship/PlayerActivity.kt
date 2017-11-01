package com.example.charlie.battleship

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_player.*
import android.widget.Toast
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.GridView



class PlayerActivity : AppCompatActivity() {

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        val player1GridView : GridView

        player1GridView = this.findViewById(R.id.player1Grid)
        player1GridView.adapter = GridAdapter(this)

        player1GridView.onItemClickListener = object : OnItemClickListener {
           override fun onItemClick(parent: AdapterView<*>, v: View,
                            position: Int, id: Long) {
                Toast.makeText(this@PlayerActivity, "$position",
                        Toast.LENGTH_SHORT).show()
            }
        }
    }
}
