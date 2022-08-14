package com.dominic.coin_search.feature_coins.domain.models


data class CoinModel(
    val id: String = "",
    val icon: String = "",
    val marketCap: Double = 0.0,
    val name: String = "",
    val price: Double = 0.0,
    val priceChange1d: Double = 0.0,
    val priceChange1h: Double = 0.0,
    val priceChange1w: Double = 0.0,
    val volume: Double = 0.0,
    val rank: Int = -1,
    val symbol: String = ""
)
