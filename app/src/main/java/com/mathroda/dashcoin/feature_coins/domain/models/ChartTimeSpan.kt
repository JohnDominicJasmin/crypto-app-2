package com.mathroda.dashcoin.feature_coins.domain.models



sealed class ChartTimeSpan(val value: String){
    object OneHour:ChartTimeSpan(value = "1h")
    object OneDay:ChartTimeSpan(value = "24h")
    object OneWeek:ChartTimeSpan(value = "1w")
    object OneMonth:ChartTimeSpan(value = "1m")
    object ThreeMonths:ChartTimeSpan(value = "3m")
    object SixMonths:ChartTimeSpan(value = "6m")
    object OneYear:ChartTimeSpan(value = "1y")
    object All:ChartTimeSpan(value = "all")
}