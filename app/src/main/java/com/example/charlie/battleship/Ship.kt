package com.example.charlie.battleship


class Ship {
    var coords : MutableList<Int> = mutableListOf()
    var hits   : MutableList<Int> = mutableListOf()
    var isSunk = false

    fun shipIsSunk() : Boolean {
        hits.sort()
        coords.sort()
        isSunk = true
        return hits.equals(coords)
    }
}