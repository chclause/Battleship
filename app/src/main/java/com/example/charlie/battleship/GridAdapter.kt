package com.example.charlie.battleship

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class GridAdapter(getContext : Context, id: Int, large: Boolean) : BaseAdapter() {

    val context = getContext
    // Lookup game object
    var gameObject = GlobalData.gameObjectsData[id]
    val large = large
    var tiles = gameObject.player1Tiles
    var showShips = true

    // Required overrides
    override fun getItem(p0: Int): Any? { return null }
    override fun getItemId(p0: Int): Long { return 0 }

    // We want 100 tiles
    override fun getCount(): Int {
        return 100
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // Load up a new custom square image if there are none with square dimensions and fill space
        //  otherwise just set it to return
        val imageView : SquareImageView

        tiles = selectTilesToShow()

        if (convertView == null) {
            imageView = SquareImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            imageView.setPadding(0,0,0,0)
        }
        else {
            imageView = convertView as SquareImageView
        }

        val currentTile = tiles[position]
        // Figure out what each tile should be
        if (currentTile.hasSunkShip) {
            imageView.setImageResource(R.drawable.sunk)
        }
        else if (currentTile.hasShip && currentTile.beenHit) {
            imageView.setImageResource(R.drawable.black)
        }
        else if (currentTile.hasShip && showShips) {
            imageView.setImageResource(R.drawable.gray)
        }
        else if (currentTile.beenHit) {
            imageView.setImageResource(R.drawable.hit)
        }
        else {
            imageView.setImageResource(R.drawable.oceantile)
        }

        return imageView
    }

    // Helper function to select the right grid to display
    private fun selectTilesToShow() : MutableList<Tile> {
        if (large && GlobalData.player1Turn) {
            // Player 1s turn and select player 2s grids, dont show ships
            tiles = gameObject.player2Tiles
            showShips = false
        }
        else if (large && !GlobalData.player1Turn) {
            // Player 2s turn and select player 1s grid, dont show ships
            tiles = gameObject.player1Tiles
            showShips = false
        }
        else if (!large && GlobalData.player1Turn) {
            // Player 1s turn viewing own grid, show ships
            tiles = gameObject.player1Tiles
            showShips = true
        }
        else if (!large && !GlobalData.player1Turn) {
            // Player 2s turn viewing own grid, show ships
            tiles = gameObject.player2Tiles
            showShips = true
        }
        else {
            Log.e("GridAdapter", "Invalid state when setting player tiles")
        }
        return tiles
    }
}
