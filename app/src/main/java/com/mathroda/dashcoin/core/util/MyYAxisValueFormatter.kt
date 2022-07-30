package com.mathroda.dashcoin.core.util

import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class MyYAxisValueFormatter : ValueFormatter() {
    private val mFormat: DecimalFormat = DecimalFormat("###,###,##0.0")
    override fun getFormattedValue(value: Float): String {
        return "$" + mFormat.format(value.toDouble())
    }


}