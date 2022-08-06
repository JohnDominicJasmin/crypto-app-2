package com.dominic.coin_search.feature_coins.presentation.coin_detail


sealed class CoinDetailEvent{
    object CloseNoInternetDisplay: CoinDetailEvent()
    object ToggleFavoriteCoin: CoinDetailEvent()
    object LoadCoinDetail: CoinDetailEvent()
    data class SelectChartPeriod(val period: String): CoinDetailEvent()
    data class ChangeYAxisValue(val yValue: String): CoinDetailEvent()
    data class ChangeXAxisValue(val xValue: String): CoinDetailEvent()

}
