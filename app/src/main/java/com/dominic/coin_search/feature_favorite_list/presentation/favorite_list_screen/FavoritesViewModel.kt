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

    private var getNotesJob: Job? = null

    init {
        getAllCoins()

    }


    fun onEvent(event: FavoriteListEvent) {
        when(event) {

            is FavoriteListEvent.AddCoin -> {
                viewModelScope.launch {
                    runCatching{
                        favoriteUseCase.addCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "Coin saved", buttonAction = "See list"))
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
                        _eventFlow.emit(value = FavoriteListUiEvent.ShowToastMessage(message = "Coin removed", buttonAction = "Undo"))
                        _state.update{it.copy(recentlyDeletedCoin = event.coin)}
                    }.onFailure {
                        Timber.d("Failed to delete coin")
                    }
                }
            }

            is FavoriteListEvent.RestoreDeletedCoin -> {
                viewModelScope.launch {
                    runCatching {
                        favoriteUseCase.addCoin(state.value.recentlyDeletedCoin ?: return@launch)
                    }.onSuccess {
                        _state.update { it.copy(recentlyDeletedCoin = null) }
                    }.onFailure {
                        Timber.d("Failed to restore coin")
                    }

                }
            }
        }
    }

    private fun getAllCoins() {
        getNotesJob?.cancel()
        getNotesJob = favoriteUseCase.getAllCoins().onEach { coins ->
            _state.update { it.copy(coins = coins) }
        }.launchIn(viewModelScope)
    }


}