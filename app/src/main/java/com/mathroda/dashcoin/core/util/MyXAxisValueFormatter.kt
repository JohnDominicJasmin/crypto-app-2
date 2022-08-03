package com.mathroda.dashcoin.core.util

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MyXAxisValueFormatter(var format: String = "HH:mm") : ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
        val date = Date(value.toLong() * 1000)
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date)

    }
}