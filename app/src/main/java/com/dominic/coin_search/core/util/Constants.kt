package com.dominic.coin_search.core.util

import androidx.datastore.preferences.core.stringPreferencesKey
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

object Constants {
    const val COIN_STATS_BASE_URL = "https://api.coinstats.app/public/"
    const val COIN_PAPRIKA_BASE_URL = "https://api.coinpaprika.com/"
    const val PARAM_COIN_ID = "coinId" //default value for get coin by id parameter
    const val DATABASE_NAME = "coins_db"
    const val TRENDING_NEWS = "trending"
    const val HAND_PICKED = "handpicked"
    const val LATEST_NEWS = "latest"
    const val BULLISH_NEWS = "bullish"
    const val BEARISH_NEWS = "bearish"
    const val LAST_HOURS = 24

    const val HEADER_CACHE_CONTROL = "Cache-Control"
    const val HEADER_PRAGMA = "Pragma"
    const val VISIBLE_ITEM_COUNT: Int = 12
    val CURRENCY = stringPreferencesKey("currency_using")
    val CURRENCY_SYMBOL = stringPreferencesKey("currency_symbol_using")
    val CHART_PERIOD = stringPreferencesKey("chart_period")

    val UPDATE_INTERVAL = 35.seconds

    const val PRICE_ANIMATION_INTERVAL = 800
    const val COINS_LIMIT = 500
    const val GOOGLE_SEARCH_QUERY = "https://www.google.com/search?q="
    private const val SECOND_MILLIS = 1000
    const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    const val DAY_MILLIS = 24 * HOUR_MILLIS

}