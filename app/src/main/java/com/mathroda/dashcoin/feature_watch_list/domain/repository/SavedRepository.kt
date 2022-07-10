package com.mathroda.dashcoin.feature_watch_list.domain.repository

import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import kotlinx.coroutines.flow.Flow

interface SavedRepository {
    suspend fun insertCoin(coins: CoinDetailModel)

    suspend fun deleteCoin(coins: CoinDetailModel)

    fun getAllCoins(): Flow<List<CoinDetailModel>>
}