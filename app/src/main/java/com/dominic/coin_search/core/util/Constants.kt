package com.dominic.coin_search.core.util

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

object Constants {
    const val COIN_STATS_API_URL = "https://api.coinstats.app/public/"
    const val COIN_PAPRIKA_API_URL = "https://api.coinpaprika.com/"
    const val EXCHANGE_RATE_API_URL = "https://api.exchangerate.host/"
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
     val DATA_STORE_INTRO_SLIDER_KEY = booleanPreferencesKey("intro_slider_completed")
    const val USER_NOT_FOUND = "ERROR_USER_NOT_FOUND"
    const val FACEBOOK_CONNECTION_FAILURE = "CONNECTION_FAILURE: CONNECTION_FAILURE"
    const val IMAGE_SMALL_SIZE = "=s96-c"
    const val IMAGE_LARGE_SIZE = "=s400-c"
    const val REGEX_NUMBER_VALUE="[0-9]"
    const val REGEX_SPECIAL_CHARACTERS_VALUE = "[@!#$%&*()_+=|<>?{}\\[\\]~]"
    const val REGEX_EMAIL_VALUE =
        "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@" +
        "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
        "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\." +
        "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?" +
        "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|" +
        "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"

    const val MINIMUM_NUMBER_OF_CHARACTERS = 3
    const val PHONE_NUMBER_NUMBER_OF_CHARACTERS: Int = 10
    const val PASSWORD_MINIMUM_NUMBER_OF_CHARACTERS = 8

    const val TIMER_COUNTS: Long = 90000
    const val ONE_SECOND_TO_MILLIS: Long = 1000
    const val REFRESH_EMAIL_INTERVAL: Long = 1000

    const val GOOGLE_SIGN_IN_REQUEST_CODE: Int = 1
    const val EMAIL_AUTH_VM_STATE_KEY = "email_auth_vm_state_key"
    const val SIGN_IN_VM_STATE_KEY = "sign_in_vm_state_key"
    const val SIGN_UP_VM_STATE_KEY = "sign_up_vm_state_key"
}