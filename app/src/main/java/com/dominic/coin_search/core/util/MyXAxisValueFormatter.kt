package com.dominic.coin_search.core.util

import android.annotation.SuppressLint
import com.dominic.coin_search.core.util.Formatters.millisToDate
import com.github.mikephil.charting.formatter.ValueFormatter


class MyXAxisValueFormatter(var format: String = "HH:mm") : ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {

            return (value.toLong() * 1000).millisToDate(timeFormat = format)
    }

}