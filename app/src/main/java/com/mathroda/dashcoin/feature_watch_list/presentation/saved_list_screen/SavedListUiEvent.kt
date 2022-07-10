package com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen

sealed class SavedListUiEvent {
    data class ShowSnackbar(val message: String,val buttonText: String):SavedListUiEvent()
}