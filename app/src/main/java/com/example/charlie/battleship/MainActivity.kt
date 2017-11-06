package com.example.charlie.battleship

import android.Manifest
import java.util.Random
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var writePermissionCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Permissions
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writePermissionCode)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writePermissionCode)
            }
        }
        GlobalData.loadGameObjectDataset()

        // Button that starts a new game
        newGame.setOnClickListener {
            val gameObject = createGame()
            gameObject.generateP1Tiles()
            gameObject.generateP2Tiles()
            GlobalData.gameObjectsData.add(gameObject)
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            intent.putExtra("GAME", GlobalData.gameObjectsData.indexOf(gameObject))
            startActivity(intent)
        }
    }


    /**********************************
     * Create a new game
     *********************************/
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
        val start_coord     = get_rand()
        val start_direction = get_direction()
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

    fun get_direction() : Direction {
        val i = Random().nextInt(4)
        when (i) {
            0 -> return Direction.UP
            1 -> return Direction.RIGHT
            2 -> return Direction.DOWN
            3 -> return Direction.LEFT
        }
        return Direction.DOWN
    }

    fun get_rand() : Int {
        val i = Random().nextInt(100)
        Log.e("RANDOM NUM", "$i")
        return i
    }
    /*********************************
     * End Create game functions
     *********************************/
}
