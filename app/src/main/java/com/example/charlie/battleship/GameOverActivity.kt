package com.example.charlie.battleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_main.*

class GameOverActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_over)
        val player1Wins = intent.getBooleanExtra("WON", true)
        var winText: String
        if (player1Wins) {
            winText = "PLAYER 1 WINS!"
        }
        else {
            winText = "PLAYER 2 WINS!"
        }
        playerWinText.setText(winText)

        // Go back to main activity
        homeButton.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }

        playAgainButton.setOnClickListener {
            val gameObject = createGame()
            gameObject.generateP1Tiles()
            gameObject.generateP2Tiles()
            GlobalData.gameObjectsData.add(gameObject)
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.putExtra("GAME", GlobalData.gameObjectsData.indexOf(gameObject))
            startActivity(intent)
        }
    }


    fun createGame() : GameObject {
        val gameObject = GameObject()

        // Make ships
        gameObject.player1Ships = generateShips()
        gameObject.player2Ships = generateShips()

        // Make tiles
        gameObject.generateP1Tiles()
        gameObject.generateP2Tiles()
        return gameObject
    }

    fun generateShips() : MutableList<Ship> {
        val ships = mutableListOf<Ship>()
        ships.add(makeShip(2, false))
        ships.add(makeShip(3, false))
        ships.add(makeShip(3, true))
        ships.add(makeShip(4, false))
        ships.add(makeShip(5, false))
        return ships
    }

    fun makeShip(size: Int, second: Boolean) : Ship {
        var ship = Ship()

        if (size == 2) {
            ship.coords = mutableListOf(25, 26)
        }
        else if(size == 3 && second) {
            ship.coords = mutableListOf(99, 89, 79)
        }
        else if (size == 3 && !second) {
            ship.coords = mutableListOf(36, 37, 38)
        }
        else if (size == 4) {
            ship.coords = mutableListOf(50, 40, 30, 20)
        }
        else {
            ship.coords = mutableListOf(70, 71, 72, 73, 74)
        }
        return ship
    }
}
