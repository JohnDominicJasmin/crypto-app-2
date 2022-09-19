package com.dominic.coin_search.data.databaes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.dominic.coin_search.feature_favorites.data.local.FavoritesDatabase
import com.dominic.coin_search.feature_favorites.data.local.FavoritesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class DashCoinModelDtoModelDatabaseTest {
    private lateinit var favoritesDao: FavoritesDao
    private lateinit var favoritesDatabase: FavoritesDatabase


    @Before
    fun setUp() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            favoritesDatabase = Room.inMemoryDatabaseBuilder(
                context,
                FavoritesDatabase::class.java
            ).allowMainThreadQueries().build()

        favoritesDao = favoritesDatabase.dao
    }

    @After
    fun tearDown() {
        favoritesDatabase.close()
    }

    @Test
    fun insertCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        favoritesDao.insertCoin(coinById)

        favoritesDao.getAllCoins().onEach {
            assertThat(it).isEqualTo(coinById)
        }
    }

    @Test
    fun deleteCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        favoritesDao.insertCoin(coinById)
        favoritesDao.deleteCoin(coinById)

        favoritesDao.getAllCoins().onEach {
            assertThat(it).isEmpty()
        }
    }
}