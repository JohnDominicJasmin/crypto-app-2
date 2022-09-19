package com.dominic.coin_search.feature_favorites.presentation.favorites_screen

sealed class FavoritesUiEvent {
    data class ShowToastMessage(val message: String):FavoritesUiEvent()
}