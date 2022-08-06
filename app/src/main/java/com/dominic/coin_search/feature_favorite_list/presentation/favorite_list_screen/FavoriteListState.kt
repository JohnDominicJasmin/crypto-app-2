package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel

data class FavoriteListState(
    val coins: List<CoinDetailModel> = emptyList(),
    val recentlyDeletedCoin: CoinDetailModel? = null,
)
