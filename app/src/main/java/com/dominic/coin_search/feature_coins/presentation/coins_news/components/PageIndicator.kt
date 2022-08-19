package com.dominic.coin_search.feature_coins.presentation.coins_news.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.ui.theme.GreenBlue600
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.PagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState, modifier: Modifier) {
    HorizontalPagerIndicator(
        pagerState = pagerState,
        modifier = modifier,
        activeColor = Color.LightGray,
        inactiveColor = GreenBlue600,
        indicatorWidth = 8.dp,
        indicatorHeight = 8.dp,
        spacing = 6.dp,
        indicatorShape = CircleShape
    )
}