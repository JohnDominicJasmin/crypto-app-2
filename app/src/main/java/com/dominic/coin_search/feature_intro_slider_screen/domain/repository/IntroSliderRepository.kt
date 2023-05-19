package com.dominic.coin_search.feature_intro_slider_screen.domain.repository

import kotlinx.coroutines.flow.Flow

interface IntroSliderRepository {
    fun userCompletedWalkThrough(): Flow<Boolean>
    suspend fun setUserCompletedWalkThrough()
}