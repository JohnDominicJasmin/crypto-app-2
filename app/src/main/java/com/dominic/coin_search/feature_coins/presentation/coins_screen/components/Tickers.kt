package com.dominic.coin_search.feature_coins.presentation.coins_screen.components

import androidx.compose.animation.core.*
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.TextUnit


@Composable
fun MarqueeText(
    annotatedText: AnnotatedString,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    durationMs: Int = 7500,
    delayMs: Int = 3000,
    spaceRatio: Float = 0.3f,
) {
    val createText = @Composable { localModifier: Modifier ->
        Text(
            annotatedText,
            textAlign = textAlign,
            modifier = localModifier,
            color = color,
            fontSize = fontSize,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            letterSpacing = letterSpacing,
            textDecoration = textDecoration,
            lineHeight = lineHeight,
            overflow = overflow,
            softWrap = softWrap,
            maxLines = 1,
            onTextLayout = onTextLayout,
            style = style,
        )
    }
    val textLayoutInfoState = remember { mutableStateOf<TextLayoutInfo?>(null) }
    val textLayoutInfo = textLayoutInfoState.value
    val transition = rememberInfiniteTransition()
    val initialValue = 0
    val offset by if (textLayoutInfo != null) {
        val duration = durationMs * textLayoutInfo.textWidth / textLayoutInfo.containerWidth
        transition.animateValue(
            initialValue = initialValue,
            targetValue = -textLayoutInfo.textWidth,
            typeConverter = Int.VectorConverter,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = duration,
                    delayMillis = delayMs,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart
            ),
        )
    } else {
        remember { mutableStateOf(initialValue) }
    }

    SubcomposeLayout(
        modifier = modifier.clipToBounds()
    ) { constraints ->
        val infiniteWidthConstraints = constraints.copy(maxWidth = Constraints.Infinity)
        var mainText = subcompose(MarqueeLayers.MainText) {
            createText(textModifier)
        }.first().measure(infiniteWidthConstraints)

        var trailingPlaceableWithOffset: Pair<Placeable, Int>? = null
        if (mainText.width <= constraints.maxWidth) {
            mainText = subcompose(MarqueeLayers.TrailingText) {
                createText(textModifier)
            }.first().measure(constraints)
            textLayoutInfoState.value = null
        } else {
            val spacing = (constraints.maxWidth * spaceRatio).toInt()
            textLayoutInfoState.value = TextLayoutInfo(
                textWidth = mainText.width + spacing, // テキストの長さ + widthの2/3の長さ
                containerWidth = constraints.maxWidth // 画面幅
            )

            val trailingTextOffset = mainText.width + spacing + offset
            val trailingTextSpace = constraints.maxWidth - trailingTextOffset
            if (trailingTextSpace > 0) {
                trailingPlaceableWithOffset = subcompose(MarqueeLayers.TrailingText) {
                    createText(textModifier)
                }.first().measure(constraints) to trailingTextOffset
            }
        }

        layout(
            width = constraints.maxWidth,
            height = mainText.height
        ) {
            mainText.place(offset, 0)
            trailingPlaceableWithOffset?.let { (placeable, trailingTextOffset) ->
                placeable.place(trailingTextOffset, 0)
            }
        }
    }
}



private enum class MarqueeLayers { MainText, TrailingText }
private data class TextLayoutInfo(
    val textWidth: Int,
    val containerWidth: Int
)
