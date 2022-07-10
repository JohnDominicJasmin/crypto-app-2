package com.mathroda.dashcoin.feature_coins.presentation.coins_screen

import com.mathroda.dashcoin.feature_coins.presentation.coins_news.NewsUiEvent

sealed class CoinsUiEvent  {
    object ShowNoInternetScreen: CoinsUiEvent()
    data class ShowToastMessage(val message: String): CoinsUiEvent()
}