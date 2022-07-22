package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
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

@Composable
fun CoinsItem(
    isLoading: Boolean,
    context: Context,
    chartModel: ChartModel?,
    coinModel: CoinModel,
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
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(if(isLoading) 15.dp else 0.dp),
        ) {


                AsyncImage(
                    model = coinModel.icon,
                    contentDescription = "Icon",
                    modifier = defaultModifier
                        .size(52.dp)
                        .padding(end = 12.dp)
                )



                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = defaultModifier
                        .weight(1.5f)


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
                        textAlign = TextAlign.Start
                    )
                }


            CoinsChart(
                chartModel = chartModel,
                priceChange = coinModel.priceChange1w,
                context = context,
                modifier = defaultModifier
                    .height(75.dp)
                    .weight(3.0f)


            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = defaultModifier
                    .weight(2f)
            ) {

                Text(
                    fontSize = 14.sp,
                    text = "$ " + DecimalFormat("###,##0.###").format(coinModel.price.toFloat()),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = if (coinModel.priceChange1w < 0) CustomRed else CustomGreen,
                    modifier = Modifier.padding(bottom = 7.dp),
                    textAlign = TextAlign.End
                )

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
                    textAlign = TextAlign.End

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
                    textAlign = TextAlign.End
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
                    textAlign = TextAlign.End
                )




            }
        }
        Divider(color = LightGray)

    }
}