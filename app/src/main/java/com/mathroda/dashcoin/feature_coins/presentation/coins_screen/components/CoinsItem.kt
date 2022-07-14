package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
                    .size(60.dp)
                    .padding(end = 12.dp)
            )



            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(
                    text = coinModel.symbol,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite,
                    textAlign = TextAlign.Start
                )

                Row {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(2.dp))
                            .background(LighterGray)
                            .size(16.dp)
                            .align(CenterVertically)
                    ) {
                        Text(
                            text = coinModel.rank.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Gold,
                            modifier = Modifier
                                .align(Center)
                        )
                    }

                }

            }

            CoinsChart(
                chartModel = chartModel,
                oneDayChange = coinModel.priceChange1d,
                context = context,
                modifier = Modifier
                    .height(80.dp)
                    .weight(4f)


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
                    text = coinModel.priceChange1d.toString() + "%",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (coinModel.priceChange1d < 0) CustomRed else CustomGreen
                )
            }
        }
        Divider(color = LightGray)

    }
}