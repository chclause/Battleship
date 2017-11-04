package com.example.charlie.battleship

import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView


class GridAdapter(getContext : Context) : BaseAdapter() {
    val context = getContext
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
        if (convertView == null) {
            imageView = SquareImageView(context)
            imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.setPadding(0,0,0,0)
        }
        else {
            imageView = convertView as SquareImageView
        }
        // Figure out what each tile should be
        if (position % 2 == 0) {
            imageView.setImageResource(R.drawable.oceantile)
        }
        else {
            imageView.setImageResource(R.drawable.hit)
        }

        return imageView
    }
}
