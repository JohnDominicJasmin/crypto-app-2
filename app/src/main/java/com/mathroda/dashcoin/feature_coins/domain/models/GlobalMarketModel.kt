package com.mathroda.dashcoin.feature_coins.domain.models

data class GlobalMarketModel(
    val marketCapUsd: Long = 0L,
    val volume24hUsd: Long = 0L,
    val cryptocurrenciesNumber: Int = 0,
    val bitcoinDominancePercentage: Double = 0.0,

    val marketCapAllTimeHigh: Long = 0L,
    val volume24hAllTimeHigh: Long = 0L,


)
