package com.mathroda.dashcoin.feature_watch_list.data.repository

import com.mathroda.dashcoin.feature_watch_list.data.data_source.local.SavedCoinDao
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_watch_list.domain.repository.SavedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SavedRepositoryImpl @Inject constructor(
    private val dao: SavedCoinDao): SavedRepository{


    override suspend fun insertCoin(coins: CoinDetailModel) {
        dao.insertCoin(coins)
    }

    override suspend fun deleteCoin(coins: CoinDetailModel) {
        dao.deleteCoin(coins)
    }

    override fun getAllCoins(): Flow<List<CoinDetailModel>> {
        return dao.getAllCoins()
    }
}