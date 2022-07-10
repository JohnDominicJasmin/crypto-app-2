package com.mathroda.dashcoin.feature_watch_list.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mathroda.dashcoin.core.util.Constants
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel

@Database(
    entities = [CoinDetailModel::class],
    version = 1,
    exportSchema = false
)
abstract class DashCoinDatabase: RoomDatabase() {

    abstract val dao: SavedCoinDao

    companion object {
      const val DATABASE_NAME = Constants.DATABASE_NAME
    }
}