package com.mathroda.dashcoin.feature_coins.data.dto


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GlobalMarketDto(
    @SerializedName("bitcoin_dominance_percentage")
    val bitcoinDominancePercentage: Double,
    @SerializedName("cryptocurrencies_number")
    val cryptocurrenciesNumber: Int,
    @SerializedName("last_updated")
    val lastUpdated: Int,
    @SerializedName("market_cap_ath_date")
    val marketCapAthDate: String,
    @SerializedName("market_cap_ath_value")
    val marketCapAthValue: Long,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double,
    @SerializedName("market_cap_usd")
    val marketCapUsd: Long,
    @SerializedName("volume_24h_ath_date")
    val volume24hAthDate: String,
    @SerializedName("volume_24h_ath_value")
    val volume24hAthValue: Long,
    @SerializedName("volume_24h_change_24h")
    val volume24hChange24h: Double,
    @SerializedName("volume_24h_percent_from_ath")
    val volume24hPercentFromAth: Double,
    @SerializedName("volume_24h_percent_to_ath")
    val volume24hPercentToAth: Double,
    @SerializedName("volume_24h_usd")
    val volume24hUsd: Long
)