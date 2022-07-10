package com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

sealed class SavedListEvent {
    data class DeleteCoin(val coin: CoinDetailModel): SavedListEvent()
    data class AddCoin(val coin: CoinDetailModel): SavedListEvent()
    object RestoreDeletedCoin: SavedListEvent()
}