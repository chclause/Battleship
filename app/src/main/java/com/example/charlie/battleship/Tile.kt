package com.example.charlie.battleship


class Tile(c: Int) {
    var hasShip : Boolean = false
    var beenHit : Boolean = false
    var coord : Int = c
}