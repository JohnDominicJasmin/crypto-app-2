package com.mathroda.dashcoin.feature_watch_list.data.data_source.local

import androidx.room.*
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import kotlinx.coroutines.flow.Flow


@Dao
interface SavedCoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinsById: CoinDetailModel)

    @Delete
    suspend fun deleteCoin(coinDetailModel: CoinDetailModel)

    @Query("SELECT * FROM CoinDetailModel")
    fun getAllCoins(): Flow<List<CoinDetailModel>>
}