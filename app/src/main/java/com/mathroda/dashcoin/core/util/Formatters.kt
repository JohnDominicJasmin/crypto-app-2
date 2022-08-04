package com.mathroda.dashcoin.core.util

import android.annotation.SuppressLint
import com.mathroda.dashcoin.feature_coins.domain.models.ChartTimeSpan
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun periodToTimeSpan(chartPeriod: String): String {
    return when(chartPeriod){
        ChartTimeSpan.OneDay.value -> {
            "HH:mm"
        }
        ChartTimeSpan.OneWeek.value -> {
            "EEE"
        }
        ChartTimeSpan.OneMonth.value, ChartTimeSpan.ThreeMonths.value, ChartTimeSpan.SixMonths.value -> {
            "dd MMM"
        }
        ChartTimeSpan.OneYear.value -> {
            "MMM"
        }
        else -> {
            "MMM dd"
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun Long.millisToDate(timeFormat: String): String {
    if (this <= 0.0) {
        return ""
    }
    return SimpleDateFormat(timeFormat).format(Date(this * 1000))
}

fun Double.toFormattedPrice(): String{
    return DecimalFormat("###,##0.00").run{
        this.roundingMode = RoundingMode.UP
        this.format(this@toFormattedPrice)
    }
}


fun Long.formatToShortNumber(): String {
    return when {
        this >= 1000000000000 -> String.format("%.2f T", this / 1000000000000.0)
        this >= 1000000000 -> String.format("%.2f B", this / 1000000000.0)
        this >= 1000000 -> String.format("%.2f M", this / 1000000.0)
        this >= 1000 -> String.format("%.2f K", this / 1000.0)
        else -> this.toString()
    }
}
