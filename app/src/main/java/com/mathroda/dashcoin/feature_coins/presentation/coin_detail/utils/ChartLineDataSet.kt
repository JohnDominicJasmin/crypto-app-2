package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.ui.theme.Green800
import com.mathroda.dashcoin.ui.theme.Red900

class ChartLineDataSet {

    fun getLineDataSet(
        lineData: List<Entry>,
        label: String,
        priceChange: Double,
        context: Context
    ) =
        LineDataSet(lineData, label).apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = getColorStatus(priceChange)
            setDrawVerticalHighlightIndicator(true)
            setDrawHorizontalHighlightIndicator(false)
            highLightColor = getColorStatus(priceChange)
            fillDrawable = getBackground(priceChange,context)
            lineWidth = 2f
            setDrawFilled(true)
            setDrawCircles(false)
        }

    private fun getColorStatus(oneDayChange: Double) =
       if (oneDayChange < 0) Red900.toArgb() else Green800.toArgb()


    private fun getBackground(
        oneDayChange: Double,
        context: Context
    ) =
        if (oneDayChange < 0)  {
            context.getCompatDrawable(R.drawable.background_negative_chart)
        } else  context.getCompatDrawable(R.drawable.background_positive_chart)


}