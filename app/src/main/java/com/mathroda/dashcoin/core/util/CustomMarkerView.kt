package com.mathroda.dashcoin.core.util

import android.content.Context
import android.view.View
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.mathroda.dashcoin.R

class CustomMarkerView constructor(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {
    var yEntry = 0f
        private set
    private val tvContent: View = findViewById(R.id.circleIndicator)
    override fun refreshContent(e: Entry, highlight: Highlight) {
        yEntry = e.y
        tvContent.visibility = VISIBLE
        super.refreshContent(e, highlight)
    }

    private var mOffset: MPPointF? = null
    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-(width / 2)).toFloat())
        }
        return mOffset!!
    }

}