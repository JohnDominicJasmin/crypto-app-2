package com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.read_intro_slider

import com.dominic.coin_search.feature_intro_slider_screen.domain.repository.IntroSliderRepository
import kotlinx.coroutines.flow.Flow

class UserCompletedWalkThroughUseCase(
    private val repository: IntroSliderRepository) {

    operator fun invoke():Flow<Boolean> =
        repository.userCompletedWalkThrough()


}