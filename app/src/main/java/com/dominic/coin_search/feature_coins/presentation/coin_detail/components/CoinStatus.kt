package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.dominic.coin_search.ui.theme.Green999
import com.dominic.coin_search.ui.theme.Red950

@Composable
fun Status(isActive: Boolean, modifier: Modifier) {
    Text(
        text = if (isActive) "Active" else "Inactive",
        color = if (isActive) Green999 else Red950,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.End,
        style = MaterialTheme.typography.body2,
        modifier = modifier,
        fontWeight = FontWeight.Medium
    )
}