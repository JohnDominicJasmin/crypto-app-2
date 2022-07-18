package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.mathroda.dashcoin.feature_coins.domain.models.ChartModel
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.ui.theme.*

@Composable
fun CoinsItem(
    context: Context,
    chartModel: ChartModel?,
    coinModel: CoinModel,
    onItemClick: (CoinModel) -> Unit
) {


    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .clickable { onItemClick(coinModel) },
            verticalAlignment = CenterVertically,
        ) {


            AsyncImage(
                model = coinModel.icon,
                contentDescription = "Icon",
                modifier = Modifier
                    .size(52.dp)
                    .padding(end = 12.dp)
            )



            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(overflow = TextOverflow.Ellipsis,
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)){
                            append(coinModel.symbol+"\n")
                        }

                        withStyle(style = SpanStyle(color = Black500, fontSize = 12.sp, fontWeight = FontWeight.Light)){
                            append(coinModel.name)
                        }
                    },
                    textAlign = TextAlign.Start
                )



            }

            CoinsChart(
                chartModel = chartModel,
                oneDayChange = coinModel.priceChange1d,
                context = context,
                modifier = Modifier
                    .height(75.dp)
                    .weight(3.8f)


            )

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    text = "$" + coinModel.price.toFloat().toString(),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                Text(
                    text = (if(coinModel.priceChange1d < 0) "" else "+") + coinModel.priceChange1d.toString() + "%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (coinModel.priceChange1d < 0) CustomRed else CustomGreen
                )
            }
        }
        Divider(color = LightGray)

    }
}