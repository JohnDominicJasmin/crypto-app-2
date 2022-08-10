package com.dominic.coin_search.feature_coins.presentation.coin_detail


sealed class CoinDetailEvent{
    object CloseNoInternetDisplay: CoinDetailEvent()
    object ToggleFavoriteCoin: CoinDetailEvent()
    object LoadCoinDetail: CoinDetailEvent()
    data class SelectChartPeriod(val period: String): CoinDetailEvent()
    data class AddChartPrice(val price: String): CoinDetailEvent()
    data class AddChartDate(val date: String): CoinDetailEvent()
    object ClearChartPrice: CoinDetailEvent()
    object ClearChartDate: CoinDetailEvent()
}
