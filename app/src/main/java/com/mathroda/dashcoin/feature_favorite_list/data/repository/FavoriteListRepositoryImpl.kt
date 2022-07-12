package com.mathroda.dashcoin.feature_favorite_list.data.repository

import com.mathroda.dashcoin.feature_favorite_list.data.data_source.local.FavoriteListDao
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.feature_favorite_list.domain.repository.FavoriteListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteListRepositoryImpl @Inject constructor(
    private val dao: FavoriteListDao): FavoriteListRepository{


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