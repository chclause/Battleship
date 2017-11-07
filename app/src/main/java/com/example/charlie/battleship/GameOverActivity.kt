package com.example.charlie.battleship

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_game_over.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

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
        // Generate a new game
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

    override fun onBackPressed() {}

    // Same functions from MainActivity, probably move this all into the gameObject class
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
        //var ship = Ship()
        val otherCoords = mutableListOf<Int>()
        ships.add(makeShip(2, otherCoords))
        ships.add(makeShip(3, otherCoords))
        ships.add(makeShip(3, otherCoords))
        ships.add(makeShip(4, otherCoords))
        ships.add(makeShip(5, otherCoords))
        return ships
    }

    fun makeShip(size: Int, oldCoords: MutableList<Int>) : Ship {
        var makingShip = true
        val ship = Ship()
        while (makingShip) {
            ship.coords.clear()
            var increment = 1
            val startCoord = get_rand(100)
            var lastCoord = startCoord
            val direction = get_rand(10)
            if (startCoord < 5 || (startCoord % 10 < 5)) {
                if (direction % 2 == 0) {
                    increment = 10
                }
                ship.coords.add(startCoord)
                for (i in 1..size-1) {
                    lastCoord +=increment
                    ship.coords.add(lastCoord)
                }
            }
            else {
                if (direction % 2 == 0) {
                    increment = -10
                }
                else {
                    increment = -1
                }
                ship.coords.add(startCoord)
                lastCoord = startCoord
                for (i in 1..size-1) {
                    lastCoord += increment
                    ship.coords.add(lastCoord)
                }
            }
            for (coord in ship.coords) {
                if (oldCoords.contains(coord) || coord > 99 || coord < 0) {
                    makingShip = true
                    break
                }
                else {
                    makingShip = false
                }
            }
        }
        ship.coords.forEach {
            oldCoords.add(it)
        }
        return ship
    }

    fun get_rand(max: Int) : Int {
        val i = Random().nextInt(max)
        return i
    }
}
