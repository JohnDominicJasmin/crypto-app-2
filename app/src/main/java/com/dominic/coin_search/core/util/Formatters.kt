package com.dominic.coin_search.core.util

import android.annotation.SuppressLint
import com.dominic.coin_search.core.util.Constants.DAY_MILLIS
import com.dominic.coin_search.core.util.Constants.HOUR_MILLIS
import com.dominic.coin_search.core.util.Constants.MINUTE_MILLIS
import com.dominic.coin_search.feature_coins.domain.models.chart.ChartTimeSpan
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
object Formatters {
    fun periodToTimeSpan(chartPeriod: String): String {
        return when (chartPeriod) {
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
        return if (this <= 0.0) {
            ""
        } else {
            SimpleDateFormat(timeFormat).format(Date(this))
        }
    }

    fun Double.toFormattedPrice(): String {
        return DecimalFormat("###,##0.00").run {
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

    private fun currentDate(): Date {
        val calendar = Calendar.getInstance()
        return calendar.time
    }

    fun Long.toTimeAgo(): String {
        var time = Date(this).time
        if (time < 1000000000000L) {
            time *= 1000
        }

        val now = currentDate().time
        if (time > now || time <= 0) {
            return "in the future"
        }

        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> "just now"
            diff < 2 * MINUTE_MILLIS -> "1m"
            diff < 60 * MINUTE_MILLIS -> "${diff / MINUTE_MILLIS}m"
            diff < 2 * HOUR_MILLIS -> "1h"
            diff < 24 * HOUR_MILLIS -> "${diff / HOUR_MILLIS}h"
            diff < 48 * HOUR_MILLIS -> "1d"
            else -> "${diff / DAY_MILLIS}d"
        }
    }
}
