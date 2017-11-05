package com.example.charlie.battleship


class Ship {
    var coords : MutableList<Int> = mutableListOf()
    var hits   : MutableList<Int> = mutableListOf()

    lateinit var direction : Direction

    fun size() : Int {
        return coords.size
    }

    fun shipIsSunk() : Boolean {
        hits.sort()
        coords.sort()
        return hits.equals(coords)
    }
}