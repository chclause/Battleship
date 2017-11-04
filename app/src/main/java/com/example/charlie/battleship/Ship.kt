package com.example.charlie.battleship

/**
 * Created by Charlie on 11/3/2017.
 */
class Ship {
    var coords : MutableList<Int> = mutableListOf()

    fun size() : Int {
        return coords.size
    }
}