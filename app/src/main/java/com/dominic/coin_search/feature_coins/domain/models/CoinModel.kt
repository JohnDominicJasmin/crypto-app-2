package com.dominic.coin_search.feature_coins.domain.models


data class CoinModel(
    val id: String,
    val icon: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    val volume: Double,
    val rank: Int,
    val symbol: String
)
