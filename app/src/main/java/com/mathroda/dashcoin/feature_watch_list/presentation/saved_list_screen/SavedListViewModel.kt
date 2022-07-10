package com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mathroda.dashcoin.feature_watch_list.domain.use_case.SavedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SavedListViewModel @Inject constructor(
    private val savedUseCase: SavedUseCase
): ViewModel() {

    private val _state = mutableStateOf(SavedListState())
    val state by _state

    private val _eventFlow = MutableSharedFlow<SavedListUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var getNotesJob: Job? = null

    init {
        getAllCoins()
    }

//todo: add event flows
    fun onEvent(event: SavedListEvent) {
        when(event) {



            is SavedListEvent.AddCoin -> {
                viewModelScope.launch {
                    runCatching{
                        savedUseCase.addCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = SavedListUiEvent.ShowSnackbar(message = "Coin Saved Successfully", buttonText = "See list"))
                    }.onFailure {
                        Timber.d("Failed to save coin")
                    }
                }
            }

            is SavedListEvent.DeleteCoin -> {
                viewModelScope.launch {
                    runCatching {
                        savedUseCase.deleteCoin(event.coin)
                    }.onSuccess {
                        _eventFlow.emit(value = SavedListUiEvent.ShowSnackbar(message = "You have deleted a coin", buttonText = "Undo"))
                        _state.value = state.copy(recentlyDeletedCoin = event.coin)
                    }.onFailure {
                        Timber.d("Failed to delete coin")
                    }
                }
            }

            is SavedListEvent.RestoreDeletedCoin -> {
                viewModelScope.launch {
                    runCatching {
                        savedUseCase.addCoin(state.recentlyDeletedCoin ?: return@launch)
                    }.onSuccess {
                        _state.value = state.copy(recentlyDeletedCoin = null)
                    }.onFailure {
                        Timber.d("Failed to restore coin")
                    }

                }
            }
        }
    }

    private fun getAllCoins() {
        getNotesJob?.cancel()
        getNotesJob = savedUseCase.getAllCoins().onEach {
            _state.value = SavedListState(it)
        }.launchIn(viewModelScope)
    }

}