package com.mathroda.dashcoin.core.util

import android.content.Context
import android.view.DragEvent
import android.view.View
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import com.mathroda.dashcoin.R

class CustomMarkerView constructor(context: Context, layoutResource: Int) :
    MarkerView(context, layoutResource) {


    val yEntry: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }
    val xEntry: MutableLiveData<Double> by lazy { MutableLiveData<Double>() }

    private val circleIndicator: View = findViewById(R.id.circleIndicator)

    override fun setOnTouchListener(l: OnTouchListener?) {

        super.setOnTouchListener(l)
    }

    override fun refreshContent(e: Entry, highlight: Highlight) {
        yEntry.value = e.y.toDouble()
        xEntry.value = e.x.toDouble()
        circleIndicator.visibility = VISIBLE

        super.refreshContent(e, highlight)
    }


    private var mOffset: MPPointF? = null
    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-(height / 2)).toFloat())
        }
        return mOffset!!
    }

}