package com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

data class SavedListState(
    val coin: List<CoinDetailModel> = emptyList(),
    val recentlyDeletedCoin: CoinDetailModel? = null,
    val snackbarVisible: Boolean = false
)
