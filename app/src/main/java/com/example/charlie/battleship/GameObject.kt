package com.example.charlie.battleship


import com.google.gson.GsonBuilder


class GameObject {
    var player1Tiles = mutableListOf<Tile>()
    var player2Tiles = mutableListOf<Tile>()

    var player1Ships = mutableListOf<Ship>()
    var player2Ships = mutableListOf<Ship>()

    var player1SunkShips = mutableListOf<Ship>()
    var player2SunkShips = mutableListOf<Ship>()

    var player1Turn = GlobalData.player1Turn

    var status = "PLAYING"
    var player1Wins = false

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
        return (player1SunkShips.size == 5 || player2SunkShips.size == 5)
    }

    /*
        Saving functionality using gson
     */
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

    fun saveP1SunkShips(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player1SunkShips)
    }

    fun saveP2SunkShips(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player2SunkShips)
    }

    fun playerTurn(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(GlobalData.player1Turn)
    }

    fun saveStatus(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(status)
    }

    fun saveP1Wins(): String {
        val gson = GsonBuilder().setPrettyPrinting().create()
        return gson.toJson(player1Wins)
    }

}