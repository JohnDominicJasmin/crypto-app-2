package com.dominic.coin_search.feature_favorites.presentation.favorites_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.feature_favorites.domain.use_case.FavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteUseCase: FavoriteUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(FavoriteListState())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<FavoritesUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getCoinsJob: Job? = null
    private var getNewsJob: Job? = null

    init {
        getSavedCoins()
        getSavedNews()
    }


    fun onEvent(event: FavoritesEvent) {
        when(event) {

            is FavoritesEvent.AddCoin -> {
                viewModelScope.launch {
                    runCatching{
                        favoriteUseCase.addCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoritesUiEvent.ShowToastMessage(message = "Coin Added"))
                    }.onFailure {
                        Timber.d("Failed to save coin")
                    }
                }
            }

            is FavoritesEvent.DeleteCoin -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.deleteCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoritesUiEvent.ShowToastMessage(message = "Coin Removed"))
                    }.onFailure {
                        Timber.d("Failed to delete coin")
                    }
                }
            }

            is FavoritesEvent.AddNews -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.addNews(event.news)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoritesUiEvent.ShowToastMessage(message = "News Added"))
                    }.onFailure {
                        Timber.d("Failed to save news")
                    }
                }
            }

            is FavoritesEvent.DeleteNews -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.deleteNews(event.news)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoritesUiEvent.ShowToastMessage(message = "News Removed"))
                    }.onFailure {
                        Timber.d("Failed to delete news")
                    }
                }
            }



        }
    }

    private fun getSavedCoins() {
        getCoinsJob?.cancel()
        getCoinsJob = favoriteUseCase.getCoins().onEach { coins ->
            _state.update { it.copy(coinDetails = CoinDetails(coins)) }
        }.launchIn(viewModelScope)
    }


    private fun getSavedNews() {
        getNewsJob?.cancel()
        getNewsJob = favoriteUseCase.getNews().onEach { news ->
            _state.update { it.copy(news = News(news)) }
        }.launchIn(viewModelScope)
    }


}