package com.dominic.coin_search.feature_intro_slider_screen.repository

import android.content.Context
import com.dominic.coin_search.core.util.Constants.DATA_STORE_INTRO_SLIDER_KEY
import com.dominic.coin_search.core.util.extension.dataStore
import com.dominic.coin_search.core.util.extension.editData
import com.dominic.coin_search.core.util.extension.getData
import com.dominic.coin_search.feature_intro_slider_screen.domain.repository.IntroSliderRepository
import kotlinx.coroutines.flow.Flow


class IntroSliderRepositoryImpl(context: Context) : IntroSliderRepository {
    private var dataStore = context.dataStore

    override fun userCompletedWalkThrough(): Flow<Boolean> {
        return dataStore.getData(key = DATA_STORE_INTRO_SLIDER_KEY, defaultValue = false)
    }

    override suspend fun setUserCompletedWalkThrough() {
        dataStore.editData(DATA_STORE_INTRO_SLIDER_KEY, true)
    }

}