package com.example.charlie.battleship

import android.util.Log
import org.json.JSONArray
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import java.util.*


class GameObject {
    var player1Tiles = mutableListOf<Tile>()
    var player2Tiles = mutableListOf<Tile>()

    var player1Ships = mutableListOf<Ship>()
    var player2Ships = mutableListOf<Ship>()

    var player1SunkShips = mutableListOf<Ship>()
    var player2SunkShips = mutableListOf<Ship>()

    var player1Turn = GlobalData.player1Turn

    var player1Wins = false
    var player2Wins = false

    fun generateP1Tiles() {
        for (i in 0..100) {
            val tile = Tile(i)

            for (ship in player1Ships) {
                if (i in ship.coords) {
                    tile.hasShip = true
                }
            }

            tile.coord = i
            player1Tiles.add(tile)
        }
    }

    fun generateP2Tiles() {
        for (i in 0..100) {
            val tile = Tile(i)

            for (ship in player2Ships) {
                if (i in ship.coords) {
                    tile.hasShip = true
                }
            }

            tile.coord = i
            player2Tiles.add(tile)
        }
    }

    fun updateShips(position: Int) : Boolean {
        if (GlobalData.player1Turn) {
            for(ship in player2Ships) {
                if (ship.coords.contains(position)) {
                    ship.hits.add(position)
                    if (ship.shipIsSunk()) {
                        player2SunkShips.add(ship)
                        for (c in ship.coords) {
                            player2Tiles[c].hasSunkShip = true
                        }
                    }
                    break
                }
            }
        }
        else {
            for(ship in player1Ships) {
                if (ship.coords.contains(position)) {
                    ship.hits.add(position)
                    if (ship.shipIsSunk()) {
                        player1SunkShips.add(ship)
                        for (c in ship.coords) {
                            player1Tiles[c].hasSunkShip = true
                        }
                    }
                    break
                }
            }
        }
        Log.e("PLAYER 1 SHIPS, ", (player1SunkShips.size == 5 || player2SunkShips.size == 5).toString())
        return (player1SunkShips.size == 5 || player2SunkShips.size == 5)
    }

    fun saveP1Ships(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player1Ships)
    }

    fun saveP2Ships(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player2Ships)
    }

    fun saveP1Tiles(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player1Tiles)
    }

    fun saveP2Tiles(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player2Tiles)
    }

    fun playerTurn(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(GlobalData.player1Turn)
    }

    fun saveGame(): String? {
        val gson = GsonBuilder().setPrettyPrinting().create()
        var game = ""
        // Serialize ships
        val p1Ships = gson.toJson(player1Ships)
        val p2Ships = gson.toJson(player2Ships)
        // Serialize tiles
        val p1Tiles = gson.toJson(player1Tiles)
        val p2Tiles = gson.toJson(player2Tiles)

        val p1Wins = gson.toJson(player1Wins)
        val p2Wins = gson.toJson(player2Wins)

        val timeStamp = gson.toJson(Date().time)

        //game = p1Ships + "\n" + p2Ships + "\n" + p1Tiles + "\n" + p2Tiles + "\n" + p1Wins + "\n" + p2Wins + "\n" + timeStamp + "\n"
        game = p1Ships

        val deSerialized: MutableList<Ship> = gson.fromJson(p1Ships, object : TypeToken<MutableList<Ship>>() {}.type)
        return game
    }
}