package com.dominic.coin_search.feature_coins.domain.models.coin

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CoinDetailModel (
    @PrimaryKey
    val rank: Int,
    val availableSupply: Double,
    val icon: String,
    val id: String,
    val marketCap: Double,
    val name: String,
    val price: Double,
    val priceChange1d: Double,
    val priceChange1h: Double,
    val priceChange1w: Double,
    val symbol: String,
    val totalSupply: Double,
    val twitterUrl: String? = null,
    val volume: Double,
    val websiteUrl: String? = null,
    val priceBtc: Double,
    )
