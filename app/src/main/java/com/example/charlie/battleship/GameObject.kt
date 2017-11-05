package com.example.charlie.battleship

import javax.microedition.khronos.opengles.GL


class GameObject {
    var player1Tiles = mutableListOf<Tile>()
    var player2Tiles = mutableListOf<Tile>()

    var player1Ships = mutableListOf<Ship>()
    var player2Ships = mutableListOf<Ship>()

    var player1SunkShips = mutableListOf<Ship>()
    var player2SunkShips = mutableListOf<Ship>()

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
                }
                if (ship.isSunk) {
                    player2SunkShips.add(ship)
                    player2Tiles[position].hasSunkShip = true
                }
            }
            checkWinConditions()
        }
        else {
            for(ship in player1Ships) {
                if (ship.coords.contains(position)) {
                    ship.hits.add(position)
                }
                if (ship.isSunk) {
                    player1SunkShips.add(ship)
                    player1Tiles[position].hasSunkShip = true
                }
            }
            checkWinConditions()
        }
        return player1Wins or player2Wins
    }

    private fun checkWinConditions() {
        if (player1SunkShips.size == 5) {
            player2Wins = true
        }
        else if (player2SunkShips.size == 5) {
            player1Wins = true
        }
    }
}