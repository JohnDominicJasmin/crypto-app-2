package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.ui.theme.Black920
import com.dominic.coin_search.ui.theme.GreenBlue600

@Composable
fun FavoriteToggleButton(
    modifier: Modifier = Modifier,
    isCoinSelected: Boolean,
    onCoinsButtonClick: (isCoinsButtonSelected: Boolean) -> Unit) {

    Surface(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .height(35.dp),
        shape = RoundedCornerShape(16.dp), color = Black920) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            ToggleButtonItem(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                "Coins",
                backgroundColor = animateColorAsState(
                    targetValue = if (isCoinSelected) GreenBlue600 else Color.Transparent,
                    animationSpec = tween(durationMillis = 600)).value,
                onClick = {
                    if (!isCoinSelected) {
                        onCoinsButtonClick(true)
                    }
                })


            ToggleButtonItem(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), "News",
                backgroundColor = animateColorAsState(
                    if (!isCoinSelected) GreenBlue600 else Color.Transparent,
                    animationSpec = tween(durationMillis = 600)).value,
                onClick = {
                    if (isCoinSelected) {
                        onCoinsButtonClick(false)
                    }
                })
        }
    }
}

@Composable
private fun ToggleButtonItem(
    modifier: Modifier,
    buttonText: String,
    backgroundColor: Color,
    onClick: () -> Unit) {
    OutlinedButton(
        modifier = modifier,
        onClick = onClick,
        contentPadding = PaddingValues(all = 0.dp),
        border = BorderStroke(0.dp, color = Color.Transparent),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = backgroundColor,
            contentColor = Color.White)) {

        Text(
            text = buttonText,
            style = MaterialTheme.typography.button,
            modifier = Modifier.padding(all = 2.dp),
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun ToggleButtonItemPreview() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

//        FavoriteToggleButton(modifier = Modifier, onCoinsButtonClick = {}, onNewsButtonClick = {})
    }
}