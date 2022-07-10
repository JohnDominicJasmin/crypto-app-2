package com.mathroda.dashcoin.feature_coins.data.dto

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel



data class CoinDetail(
    val availableSupply: Double,
    val exp: List<String>,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceBtc: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    val rank: Int,
    val symbol: String,
    val totalSupply: Double,
    val twitterUrl: String,
    val volume: Double,
    val websiteUrl: String
)

