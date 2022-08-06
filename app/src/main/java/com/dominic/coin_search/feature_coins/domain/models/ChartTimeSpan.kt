package com.dominic.coin_search.feature_coins.domain.models



sealed class ChartTimeSpan(val value: String){
    object OneDay:ChartTimeSpan(value = "24h")
    object OneWeek:ChartTimeSpan(value = "1w")
    object OneMonth:ChartTimeSpan(value = "1m")
    object ThreeMonths:ChartTimeSpan(value = "3m")
    object SixMonths:ChartTimeSpan(value = "6m")
    object OneYear:ChartTimeSpan(value = "1y")
    object All:ChartTimeSpan(value = "all")
}