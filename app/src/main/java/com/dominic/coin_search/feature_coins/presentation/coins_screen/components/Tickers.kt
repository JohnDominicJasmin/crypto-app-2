package com.dominic.coin_search.feature_coins.presentation.coins_screen.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.ui.theme.Black450


@Composable
fun MarqueeText(
    annotatedText: AnnotatedString,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val shouldAnimate by rememberSaveable {
        mutableStateOf(true)
    }
    LaunchedEffect(key1 = shouldAnimate){
        scrollState.animateScrollTo(
            scrollState.maxValue,
            animationSpec = infiniteRepeatable(
                animation = tween(18000, 200, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )


    }
    Text(
        text = annotatedText,
        color = Color.White,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        modifier = modifier.horizontalScroll(scrollState, false)
    )
}

@Preview
@Composable
fun MarqueeTextPreview() {
    MarqueeText(
        modifier = Modifier.padding(top = 5.dp, bottom = 8.dp),
        annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("Number of Cryptos: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("${999999}        |        ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("Market Cap: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("$${99999}        |        ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("24h Vol: ")
            }

            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("$${9999999}        |        ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("BTC Dominance: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("${99}%        |        ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("Market Cap All-Time High: ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("${999999}        |        ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Black450)) {
                append("Volume All-Time High(24): ")
            }
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Normal,
                    color = Color.White)) {
                append("999")
            }

        },

        )
}
