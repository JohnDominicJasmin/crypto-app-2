package com.dominic.coin_search.feature_favorite_list.data.local

import androidx.room.*
import com.dominic.coin_search.feature_coins.domain.models.CoinDetailModel
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoriteListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoin(coinsById: CoinDetailModel)

    @Delete
    suspend fun deleteCoin(coinDetailModel: CoinDetailModel)

    @Query("SELECT * FROM CoinDetailModel")
    fun getAllCoins(): Flow<List<CoinDetailModel>>
}