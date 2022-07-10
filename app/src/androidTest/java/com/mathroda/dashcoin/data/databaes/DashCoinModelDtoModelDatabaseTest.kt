package com.mathroda.dashcoin.data.databaes

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.mathroda.dashcoin.feature_watch_list.data.data_source.local.DashCoinDatabase
import com.mathroda.dashcoin.feature_watch_list.data.data_source.local.SavedCoinDao
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
    private lateinit var savedCoinDao: SavedCoinDao
    private lateinit var dashCoinDatabase: DashCoinDatabase


    @Before
    fun setUp() {
            val context = ApplicationProvider.getApplicationContext<Context>()
            dashCoinDatabase = Room.inMemoryDatabaseBuilder(
                context,
                DashCoinDatabase::class.java
            ).allowMainThreadQueries().build()

        savedCoinDao = dashCoinDatabase.dao
    }

    @After
    fun tearDown() {
        dashCoinDatabase.close()
    }

    @Test
    fun insertCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        savedCoinDao.insertCoin(coinById)

        savedCoinDao.getAllCoins().onEach {
            assertThat(it).isEqualTo(coinById)
        }
    }

    @Test
    fun deleteCoinToDatabase() = runTest {
        val coinById = FakeDataTest.coinDetailModel
        savedCoinDao.insertCoin(coinById)
        savedCoinDao.deleteCoin(coinById)

        savedCoinDao.getAllCoins().onEach {
            assertThat(it).isEmpty()
        }
    }
}