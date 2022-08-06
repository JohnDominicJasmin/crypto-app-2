package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel

sealed class FavoriteListEvent {
    data class DeleteCoin(val coin: CoinDetailModel): FavoriteListEvent()
    data class AddCoin(val coin: CoinDetailModel): FavoriteListEvent()
    object RestoreDeletedCoin: FavoriteListEvent()
}