package com.dominic.coin_search.feature_favorite_list.domain.repository

import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel
import kotlinx.coroutines.flow.Flow

interface FavoriteListRepository {
    suspend fun insertCoin(coins: CoinDetailModel)

    suspend fun deleteCoin(coins: CoinDetailModel)

    fun getAllCoins(): Flow<List<CoinDetailModel>>
}