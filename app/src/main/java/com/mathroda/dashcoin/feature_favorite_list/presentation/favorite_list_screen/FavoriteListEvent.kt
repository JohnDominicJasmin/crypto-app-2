package com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel

sealed class FavoriteListEvent {
    data class DeleteCoin(val coin: CoinDetailModel): FavoriteListEvent()
    data class AddCoin(val coin: CoinDetailModel): FavoriteListEvent()
    object RestoreDeletedCoin: FavoriteListEvent()
}