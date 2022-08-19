package com.dominic.coin_search.feature_coins.presentation.coins_news.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NewsTitleSection(modifier: Modifier = Modifier, title: String) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.subtitle1,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier.padding(start = 12.dp),
        color = Color.White
    )
}