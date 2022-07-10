package com.mathroda.dashcoin.feature_coins.domain.models



sealed class ChartTimeSpan(val value: String){
    object TimeSpan1Day:ChartTimeSpan(value = "24h")
    object TimeSpan1Week:ChartTimeSpan(value = "1w")
    object TimeSpan1Month:ChartTimeSpan(value = "1m")
    object TimeSpan3Months:ChartTimeSpan(value = "3m")
    object TimeSpan6Months:ChartTimeSpan(value = "6m")
    object TimeSpan1Year:ChartTimeSpan(value = "1y")
    object TimeSpanAll:ChartTimeSpan(value = "all")
}