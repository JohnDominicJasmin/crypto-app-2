package com.mathroda.dashcoin.core.util

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter


class MyXAxisValueFormatter(var format: String = "HH:mm") : ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
            return value.toLong().millisToDate(timeFormat = format)
    }

}