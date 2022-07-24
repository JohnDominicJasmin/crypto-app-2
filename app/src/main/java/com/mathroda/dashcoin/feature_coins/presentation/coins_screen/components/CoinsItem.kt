package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.placeholder.placeholder
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.ui.theme.*
import java.text.DecimalFormat

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CoinsItem(
    isLoading: Boolean,
    context: Context,
    chartModel: ChartModel?,
    coinModel: CoinModel,
    currencySymbol: String,
    onItemClick: (CoinModel) -> Unit
) {


    val defaultModifier = Modifier.placeholder(
        visible = isLoading,
        color = Black930,
        shape = RoundedCornerShape(4.dp),
        highlight = PlaceholderHighlight.shimmer(highlightColor = Black910),
    )


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .clickable { onItemClick(coinModel) },
            verticalAlignment = CenterVertically
        ) {


                AsyncImage(
                    model = coinModel.icon,
                    contentDescription = "Icon",
                    modifier = defaultModifier
                        .size(45.dp)
                        .padding(end = 6.dp)
                        .animateContentSize()
                )



                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = defaultModifier
                        .weight(1.2f)
                        .animateContentSize()


                ) {
                    Text(
                        overflow = TextOverflow.Ellipsis,
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = Color.White,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold)) {
                                append(coinModel.symbol + "\n")
                            }

                            withStyle(
                                style = SpanStyle(
                                    color = Black500,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Light)) {
                                append(coinModel.name)
                            }
                        },
                        textAlign = TextAlign.Start,
                        modifier = Modifier.animateContentSize()
                    )
                }


            CoinsChart(
                chartModel = chartModel,
                priceChange = coinModel.priceChange1w,
                context = context,
                modifier = defaultModifier
                    .height(75.dp)
                    .weight(2.1f)
                    .animateContentSize()

            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = defaultModifier
                    .weight(2f)
                    .animateContentSize()

            ) {



                val duration = 800
                val formattedPrice = DecimalFormat("#,###,###.##").format(coinModel.price.toFloat())
                AnimatedContent(targetState = "$currencySymbol $formattedPrice",
                    transitionSpec = {
                        slideInVertically(
                            animationSpec = tween(
                                durationMillis = duration)) { it } +
                                fadeIn(animationSpec = tween(durationMillis = duration)) with slideOutVertically(
                            animationSpec = tween(durationMillis = duration)) { -it } + fadeOut(tween(durationMillis = duration))
                    }){ price:String ->

                    Text(
                        fontSize = 14.sp,
                        text = price,
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        color = if (coinModel.priceChange1w < 0) CustomRed else CustomGreen,
                        modifier = Modifier.padding(bottom = 7.dp),
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis
                    )
                }





                Text(
                    text = buildAnnotatedString {
                         withStyle(style = SpanStyle()){
                             append((if(coinModel.priceChange1h < 0) "" else "+") + coinModel.priceChange1h.toString() + "%")
                         }
                        withStyle(style = SpanStyle(color = Blue100)){
                            append("  1H")
                        }
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.animateContentSize()

                )


                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle()){
                            append((if(coinModel.priceChange1d < 0) "" else "+") + coinModel.priceChange1d.toString() + "%")
                        }
                        withStyle(style = SpanStyle(color = Blue100)){
                            append("  1D")
                        }
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.animateContentSize()
                )


                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle()){
                                append((if(coinModel.priceChange1w < 0) "" else "+") + coinModel.priceChange1w.toString() + "%")
                        }
                        withStyle(style = SpanStyle(color = Blue100)){
                            append("  7D")
                        }
                    },
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.animateContentSize()
                )




            }
        }
        Divider(color = LightGray)

    }
}