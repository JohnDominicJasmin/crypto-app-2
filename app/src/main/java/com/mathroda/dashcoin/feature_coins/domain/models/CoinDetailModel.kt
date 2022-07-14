package com.mathroda.dashcoin.feature_coins.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinDetailModel (
    val availableSupply: Double,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,

    @PrimaryKey
    val rank: Int,
    val symbol: String,
    val totalSupply: Double,
    val twitterUrl: String? = null,
    val volume: Double,
    val websiteUrl: String? = null,
    val priceBtc: Double,
    )
