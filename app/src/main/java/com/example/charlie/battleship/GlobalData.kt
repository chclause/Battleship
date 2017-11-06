package com.example.charlie.battleship

import android.os.Environment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File


/* Object used to hold a collection of GameObjects and load/save the data set to disk */
object GlobalData {

    var gameObjectsData: MutableList<GameObject> = mutableListOf()

    var player1Turn = true

    var loaded = false


    private val canReadExternalStorage: Boolean
        get() = when (Environment.getExternalStorageState()) {
            Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY -> true
            else -> false
        }
    private val canWriteToExternalStorage: Boolean
        get() = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

    // Save the dataset, recursively delete and recreate all subdirectories, make more efficient if you have time
    // Use the Documents file directory so you can verify data looks like what you want
    fun saveGameObjectDataset() {
        if (canWriteToExternalStorage) {
            var index = 0
            val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GameObjects")

            if (!directory.exists()) {
                check(directory.mkdirs(), {"External storage was marked ad readable, but could not be created there."})
            }
            gameObjectsData.forEach {
                val subdirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GameObjects/" + index++)
                subdirectory.deleteRecursively()
                if (!subdirectory.exists()) {
                    check(subdirectory.mkdirs(), {"External storage was marked ad readable, but could not be created there."})
                }
                File(subdirectory.absolutePath + File.separator + "p1Ships" + ".txt").writeText(it.saveP1Ships())
                File(subdirectory.absolutePath + File.separator + "p2Ships" + ".txt").writeText(it.saveP2Ships())
                File(subdirectory.absolutePath + File.separator + "p1Tiles" + ".txt").writeText(it.saveP1Tiles())
                File(subdirectory.absolutePath + File.separator + "p2Tiles" + ".txt").writeText(it.saveP2Tiles())
                File(subdirectory.absolutePath + File.separator + "p1SunkShips" + ".txt").writeText(it.saveP1SunkShips())
                File(subdirectory.absolutePath + File.separator + "p2SunkShips" + ".txt").writeText(it.saveP2SunkShips())
                File(subdirectory.absolutePath + File.separator + "pTurn" + ".txt").writeText(it.playerTurn())
                File(subdirectory.absolutePath + File.separator + "status" + ".txt").writeText(it.saveStatus())
                File(subdirectory.absolutePath + File.separator + "p1Wins" + ".txt").writeText(it.saveP1Wins())
            }
        }
    }

    // Load the game objects from the file system
    fun loadGameObjectDataset() {
        var index = 0
        val directory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GameObjects")
        if (canReadExternalStorage && directory.exists()) {
            for (file in directory.listFiles()) {
                val subdirectory = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GameObjects/" + index++)
                val gameObject: GameObject = GameObject()
                for (item in subdirectory.listFiles()) {
                    // Deserialize ships
                    val p1ShipsFile = File(subdirectory.absolutePath + File.separator + "p1Ships" + ".txt")
                    gameObject.player1Ships = Gson().fromJson(p1ShipsFile.readText(), object : TypeToken<MutableList<Ship>>() {}.type)
                    val p2ShipsFile = File(subdirectory.absolutePath + File.separator + "p2Ships" + ".txt")
                    gameObject.player2Ships = Gson().fromJson(p2ShipsFile.readText(), object : TypeToken<MutableList<Ship>>() {}.type)
                    // Deserialize sunk ships
                    val p1SunkShipsFile = File(subdirectory.absolutePath + File.separator + "p1SunkShips" + ".txt")
                    gameObject.player1SunkShips = Gson().fromJson(p1SunkShipsFile.readText(), object : TypeToken<MutableList<Ship>>() {}.type)
                    val p2SunkShipsFile = File(subdirectory.absolutePath + File.separator + "p2SunkShips" + ".txt")
                    gameObject.player2SunkShips = Gson().fromJson(p2SunkShipsFile.readText(), object : TypeToken<MutableList<Ship>>() {}.type)
                    // Deserialize tiles
                    val p1TilesFile = File(subdirectory.absolutePath + File.separator + "p1Tiles" + ".txt")
                    gameObject.player1Tiles = Gson().fromJson(p1TilesFile.readText(), object : TypeToken<MutableList<Tile>>() {}.type)
                    val p2TilesFile = File(subdirectory.absolutePath + File.separator + "p2Tiles" + ".txt")
                    gameObject.player2Tiles = Gson().fromJson(p2TilesFile.readText(), object : TypeToken<MutableList<Tile>>() {}.type)
                    val playerTurnFile = File(subdirectory.absolutePath + File.separator + "pTurn" + ".txt")
                    gameObject.player1Turn = Gson().fromJson(playerTurnFile.readText(), object : TypeToken<Boolean>() {}.type)
                    val p1WinsFile = File(subdirectory.absolutePath + File.separator + "p1Wins" + ".txt")
                    gameObject.player1Wins = Gson().fromJson(p1WinsFile.readText(), object : TypeToken<Boolean>() {}.type)
                    val statusFile = File(subdirectory.absolutePath + File.separator + "status" + ".txt")
                    gameObject.status = Gson().fromJson(statusFile.readText(), object : TypeToken<String>() {}.type)
                }
                gameObjectsData.add(gameObject)
            }
        }
    }
}