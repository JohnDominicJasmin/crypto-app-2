package com.dominic.coin_search.feature_intro_slider_screen.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.feature_intro_slider_screen.domain.use_case.IntroSliderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroSliderViewModel @Inject constructor(

    private val introSliderUseCase: IntroSliderUseCase,
    private val defaultDispatcher: CoroutineDispatcher
    ) : ViewModel() {


    fun onEvent(event: IntroSliderEvent) {
        when (event) {
            is IntroSliderEvent.UserCompletedWalkThrough -> {
                viewModelScope.launch(context = defaultDispatcher) {
                    introSliderUseCase.completedIntroSliderUseCase()
                }
            }

        }
    }


}