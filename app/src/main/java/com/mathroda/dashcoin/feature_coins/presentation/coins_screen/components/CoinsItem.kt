package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.ui.theme.*
import java.text.DecimalFormat

@Composable
fun CoinsItem(
    coinModel: CoinModel,
    chartModel: ChartModel?,
    currencySymbol: String,
    onItemClick: (CoinModel) -> Unit
) {





    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(vertical = 3.dp, horizontal = 9.dp)
                .clickable { onItemClick(coinModel) },
            verticalAlignment = CenterVertically
        ) {



                Box(modifier = Modifier.padding(end = 8.dp)) {
                    AsyncImage(
                        model = coinModel.icon,
                        contentDescription = "Icon",
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )
                }

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .weight(1.6f)


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
                    )
                }


            CoinsChart(
                chartModel = chartModel,
                priceChange = coinModel.priceChange1w,
                modifier = Modifier
                    .height(70.dp)
                    .weight(2.3f)

            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(2.1f)

            ) {



          /* AnimatedContent(targetState = "$currencySymbol $formattedPrice",
                    transitionSpec = {
                        slideInVertically(
                            animationSpec = tween(
                                durationMillis = PRICE_ANIMATION_INTERVAL)) { it } +
                                fadeIn(animationSpec = tween(durationMillis = PRICE_ANIMATION_INTERVAL)) with slideOutVertically(
                            animationSpec = tween(durationMillis = PRICE_ANIMATION_INTERVAL)) { -it } + fadeOut(tween(durationMillis = PRICE_ANIMATION_INTERVAL))
                    }){ price:String ->*/


                    Text(
                        fontSize = 14.sp,
                        text = "$currencySymbol ${coinModel.price.toFormattedPrice()}",
                        style = MaterialTheme.typography.body2,
                        fontWeight = FontWeight.Bold,
                        color = if (coinModel.priceChange1w < 0) Red900 else Green800,
                        modifier = Modifier.padding(bottom = 7.dp),
                        textAlign = TextAlign.End,
                        overflow = TextOverflow.Ellipsis
                    )
//                }





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
                )




            }
        }
        Divider(color = LightGray)

    }
}
fun Double.toFormattedPrice(): String{
    return DecimalFormat("###,###.##").format(this)
}