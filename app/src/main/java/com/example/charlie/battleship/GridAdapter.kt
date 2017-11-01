package com.example.charlie.battleship

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView


class GridAdapter(getContext : Context) : BaseAdapter() {

    val context = getContext

    override fun getItem(p0: Int): Any? {
        return null
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return 100
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var imageView : ImageView
        if (convertView == null) {
            imageView = ImageView(context)
            // Figure out what these things mean
            imageView.layoutParams = ViewGroup.LayoutParams(500, 500)
            imageView.scaleType = ImageView.ScaleType.FIT_XY
            // Change to 0
            imageView.setPadding(8,8,8,8)
        }
        else {
            imageView = convertView as ImageView
        }

        if (position % 2 == 0) {
            imageView.setImageResource(R.drawable.oceantile)
        }
        else {
            imageView.setImageResource(R.drawable.hit)
        }
        return imageView
    }

}
