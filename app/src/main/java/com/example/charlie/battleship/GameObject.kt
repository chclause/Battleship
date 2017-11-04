package com.example.charlie.battleship


class GameObject {
    var player1Tiles = mutableListOf<Tile>()
    var player2Tiles = mutableListOf<Tile>()

    var player1Ships = mutableListOf<Ship>()
    var player2Ships = mutableListOf<Ship>()

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

            for (ship in player1Ships) {
                if (i in ship.coords) {
                    tile.hasShip = true
                }
            }

            tile.coord = i
            player1Tiles.add(tile)
        }
    }
}