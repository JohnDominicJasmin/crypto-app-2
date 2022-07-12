package com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

data class FavoriteListState(
    val coins: List<CoinDetailModel> = emptyList(),
    val recentlyDeletedCoin: CoinDetailModel? = null,
)
