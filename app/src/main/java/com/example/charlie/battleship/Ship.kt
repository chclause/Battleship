package com.example.charlie.battleship


class Ship {
    var coords : MutableList<Int> = mutableListOf()

    lateinit var direction : Direction

    fun size() : Int {
        return coords.size
    }
}