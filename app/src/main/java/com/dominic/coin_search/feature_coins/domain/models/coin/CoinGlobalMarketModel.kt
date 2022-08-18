package com.dominic.coin_search.feature_coins.domain.models.coin

data class CoinGlobalMarketModel(
    val marketCapUsd: Long = 0L,
    val volume24hUsd: Long = 0L,
    val cryptocurrenciesNumber: Int = 0,
    val bitcoinDominancePercentage: Double = 0.0,

    val marketCapAllTimeHigh: Long = 0L,
    val volume24hAllTimeHigh: Long = 0L,


)
