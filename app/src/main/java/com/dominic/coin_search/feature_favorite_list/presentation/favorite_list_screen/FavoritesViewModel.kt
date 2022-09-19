package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dominic.coin_search.feature_favorite_list.domain.use_case.FavoriteUseCase
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

    private val _eventFlow = MutableSharedFlow<FavoriteListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getCoinsJob: Job? = null
    private var getNewsJob: Job? = null

    init {
        getSavedCoins()
        getSavedNews()
    }


    fun onEvent(event: FavoriteListEvent) {
        when(event) {

            is FavoriteListEvent.AddCoin -> {
                viewModelScope.launch {
                    runCatching{
                        favoriteUseCase.addCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "Coin Added"))
                    }.onFailure {
                        Timber.d("Failed to save coin")
                    }
                }
            }

            is FavoriteListEvent.DeleteCoin -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.deleteCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "Coin Removed"))
                    }.onFailure {
                        Timber.d("Failed to delete coin")
                    }
                }
            }

            is FavoriteListEvent.AddNews -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.addNews(event.news)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "News Added"))
                    }.onFailure {
                        Timber.d("Failed to save news")
                    }
                }
            }

            is FavoriteListEvent.DeleteNews -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.deleteNews(event.news)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "News Removed"))
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