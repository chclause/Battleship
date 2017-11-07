package com.example.charlie.battleship

import android.content.Intent
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
        // Get the game object
        val gameID = intent.getIntExtra("GAME", GlobalData.gameObjectsData.lastIndex)
        val gameObject = GlobalData.gameObjectsData[gameID]
        var toastText = "Miss!"

        setContentView(R.layout.activity_player)

        // Set up the grid and adapter
        val player1GridView: GridView = this.findViewById(R.id.largeGrid)
        player1GridView.adapter = GridAdapter(this, gameID, true)

        val player2GridView: GridView = this.findViewById(R.id.smallGrid)
        player2GridView.adapter = GridAdapter(this, gameID, false)


        // Player has clicked a tile, see if it is a hit, then if the game should end, otherwise just end the players turn
        player1GridView.onItemClickListener = object : OnItemClickListener {
           override fun onItemClick(parent: AdapterView<*>, v: View,
                            position: Int, id: Long) {
               gameObject.status = "IN PROGRESS"
               when(GlobalData.player1Turn) {
                   true -> {
                       if (!gameObject.player2Tiles[position].beenHit) {
                           if (gameObject.player2Tiles[position].hasShip) {
                               toastText = "Player 1 Hits!"
                           }
                           else {
                               toastText = "Player 1 Misses!"
                           }
                           gameObject.player2Tiles[position].beenHit = true
                           val gameOver = gameObject.updateShips(position)
                           player1GridView.invalidateViews()
                           if (gameOver) {
                               // player1 wins
                               gameObject.player1Wins = true
                               gameObject.status = "COMPLETE"
                               GlobalData.saveGameObjectDataset()
                               val intent = Intent(applicationContext, GameOverActivity::class.java)
                               intent.putExtra("WON", true)
                               startActivity(intent)
                           }
                           else {
                               val intent = Intent(applicationContext, PassActivity::class.java)
                               intent.putExtra("GAME", GlobalData.gameObjectsData.indexOf(gameObject))
                               startActivity(intent)
                           }
                       }
                   }
                   false -> {
                       if (!gameObject.player1Tiles[position].beenHit) {
                           if (gameObject.player1Tiles[position].hasShip) {
                               toastText = "Player 2 Hits!"
                           }
                           else {
                               toastText = "Player 2 Misses!"
                           }
                           gameObject.player1Tiles[position].beenHit = true
                           player1GridView.invalidateViews()
                           val gameOver = gameObject.updateShips(position)
                           if (gameOver) {
                               // Player 2 wins
                               gameObject.player1Wins = false
                               gameObject.status = "COMPLETE"
                               GlobalData.saveGameObjectDataset()
                               val intent = Intent(applicationContext, GameOverActivity::class.java)
                               intent.putExtra("WON", false)
                               startActivity(intent)
                           }
                           else {
                               val intent = Intent(applicationContext, PassActivity::class.java)
                               intent.putExtra("GAME", GlobalData.gameObjectsData.indexOf(gameObject))
                               startActivity(intent)
                           }
                       }
                   }
               }
               // Save state and display if the player hit or missed
               GlobalData.saveGameObjectDataset()
                Toast.makeText(this@PlayerActivity, toastText,
                        Toast.LENGTH_LONG).show()
            }
        }
        // Allow the player to go home because I disabled the back button
        homeButton.setOnClickListener {
            GlobalData.saveGameObjectDataset()
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {}
}
