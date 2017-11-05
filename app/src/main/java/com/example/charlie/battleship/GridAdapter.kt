package com.example.charlie.battleship

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class GridAdapter(getContext : Context, id: Int) : BaseAdapter() {

    val context = getContext
    // Lookup game object
    var gameObject = GlobalData.gameObjectsData[id]

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
        var tiles = gameObject.player2Tiles
        if (GlobalData.player1Turn) {
            tiles = gameObject.player1Tiles
        }

        if (convertView == null) {
            imageView = SquareImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(0,0,0,0)
        }
        else {
            imageView = convertView as SquareImageView
        }

        val currentTile = tiles[position]
        // Figure out what each tile should be
        if (currentTile.hasShip && currentTile.beenHit) {
            imageView.setImageResource(R.drawable.black)
        }
        else if (currentTile.hasShip) {
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
}
