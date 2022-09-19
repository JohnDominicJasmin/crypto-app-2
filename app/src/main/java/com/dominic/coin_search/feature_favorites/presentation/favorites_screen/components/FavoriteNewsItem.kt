package com.dominic.coin_search.feature_favorites.presentation.favorites_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.dominic.coin_search.feature_coins.domain.models.news.NewsModel
import com.dominic.coin_search.feature_coins.presentation.coins_news.components.NewsItemSmall
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoriteNewsItem(
    modifier: Modifier = Modifier,
    newsModel: NewsModel,
    onItemClick: () -> Unit,
    onDeleteClick: () -> Unit) {

    val (isSavedNews, onIconSaveClick) = rememberSaveable {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()

    AnimatedVisibility(
        visible = isSavedNews,
        enter = fadeIn(initialAlpha = 0.4f)) {

        NewsItemSmall(
            modifier = modifier,
            newsModel = newsModel,
            onItemClick = onItemClick,
            onSaveClick = {
                scope.launch {
                    onIconSaveClick(!isSavedNews)
                    delay(300)
                    onDeleteClick()
                }
            },
            isSavedNews = isSavedNews
        )
    }

}