package com.mathroda.dashcoin.core.util

object Constants {
    const val BASE_URL = "https://api.coinstats.app/public/"
    const val PARAM_COIN_ID = "coinId" //default value for get coin by id parameter
    const val DATABASE_NAME = "coins_db"
    const val HANDPICKED_NEWS = "handpicked"
    const val TRENDING_NEWS = "trending"
    const val LATEST_NEWS = "latest"
    const val BULLISH_NEWS = "bullish"
    const val BEARISH_NEWS = "bearish"
    const val LAST_FIVE_HOURS = 60 //api update is every 5 minutes
}