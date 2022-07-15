package com.mathroda.dashcoin.core.util

import android.annotation.SuppressLint
import com.github.mikephil.charting.formatter.ValueFormatter
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components.millisToDate


class MyXAxisValueFormatter(var format: String = "HH:mm") : ValueFormatter() {

    @SuppressLint("SimpleDateFormat")
    override fun getFormattedValue(value: Float): String {
            return value.toLong().millisToDate(format = format)
    }
}