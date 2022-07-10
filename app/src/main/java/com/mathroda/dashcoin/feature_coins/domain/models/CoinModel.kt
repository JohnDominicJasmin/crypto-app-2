package com.mathroda.dashcoin.feature_coins.domain.models


data class CoinModel(
    val id: String,
    val icon: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val rank: Int,
    val symbol: String
)
