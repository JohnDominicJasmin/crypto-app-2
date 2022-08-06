package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

sealed class FavoriteListUiEvent {
    data class ShowSnackbar(val message: String, val buttonAction: String):FavoriteListUiEvent()
}