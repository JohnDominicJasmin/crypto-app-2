package com.dominic.coin_search.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Green800,
    background = DarkGray,
    onBackground = Color.White,
    onPrimary = LightGray
)

private val LightColorPalette = lightColors(
    primary = Green800,
    background = DarkGray,
    onBackground = Color.White,
    onPrimary = LightGray
)

@Composable
fun DashCoinTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}