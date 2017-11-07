package com.example.charlie.battleship

import android.Manifest
import java.util.Random
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {
    var writePermissionCode: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Permissions because I wrote to documents folder again so I could see the files written
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writePermissionCode)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), writePermissionCode)
            }
        }
        // If app just opened load the dataset from disk
        if (!GlobalData.loaded) {
            GlobalData.loaded = true
            GlobalData.loadGameObjectDataset()
        }
        var selectedItem: Int = -1
        var actualPosition: Int = -1
        // Set up the saved games
        loadedGames.adapter = LoadedGameListAdapter(applicationContext)

        loadedGames.onItemClickListener = object : AdapterView.OnItemClickListener {
            override fun onItemClick(parent: AdapterView<*>, v: View,
                                     position: Int, id: Long) {
                selectedItem = GlobalData.gameObjectsData.size - position - 1
                actualPosition = position
            }
        }

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

        // Load the selected game, or dont if there are none or it is complete
        loadButton.setOnClickListener {
            if (selectedItem != -1) {
                val game = GlobalData.gameObjectsData[selectedItem]
                if (game.status != "COMPLETE") {
                    GlobalData.player1Turn = game.player1Turn
                    val intent = Intent(applicationContext, PlayerActivity::class.java)
                    intent.putExtra("GAME", selectedItem)
                    startActivity(intent)
                }
            }
        }

        // Delete the selected item
        deleteButton.setOnClickListener {
            if (selectedItem != -1 && actualPosition != -1) {
                GlobalData.gameObjectsData.removeAt(selectedItem)
                val subdirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GameObjects/" + actualPosition)
                subdirectory.deleteRecursively()
                selectedItem = -1
                actualPosition = -1
                loadedGames.invalidateViews()
            }
        }
    }

    override fun onBackPressed() {}


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

    /* Generate random ship placements */
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

    /*
        Algorithm to make a ship, checks if the ship is overlapping or off the grid, decides what direction to point based on random starting coord,
            does some lazy checking for proximity to other ships
     */
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
            // Check if this is a valid ship, do some simple and lazy checking for proximity to other ships
            for (coord in ship.coords) {
                if (oldCoords.contains(coord) || coord > 99 || coord < 0
                        || oldCoords.contains(coord+1) || oldCoords.contains(coord + 10)
                        || oldCoords.contains(coord - 10) || oldCoords.contains(coord - 1)) {
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

    /* Get a random num */
    fun get_rand(max: Int) : Int {
        val i = Random().nextInt(max)
        return i
    }
    /*********************************
     * End Create game functions
     *********************************/
}
