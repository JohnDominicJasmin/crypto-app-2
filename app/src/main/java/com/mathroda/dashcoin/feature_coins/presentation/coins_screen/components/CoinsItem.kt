package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import com.mathroda.dashcoin.feature_coins.domain.models.CoinModel
import com.mathroda.dashcoin.ui.theme.*

@Composable
fun CoinsItem(
    coinModel: CoinModel,
    onItemClick: (CoinModel) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clickable { onItemClick(coinModel) },
        verticalAlignment = CenterVertically,
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(2f)
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(LighterGray)
                    .size(50.dp)
            ) {

                AsyncImage(
                    model = coinModel.icon ,
                    contentDescription = "Icon",
                    modifier = Modifier
                        .size(20.dp)
                        .align(Center)
                )
            }
        }



        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .weight(5f)
            ) {
            Text(
                text = coinModel.name,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold,
                color = TextWhite,
                textAlign = TextAlign.Start
            )

            Row {
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(2.dp))
                    .background(LighterGray)
                    .size(16.dp)
                    .align(CenterVertically)
                ) {
                    Text(
                        text = coinModel.rank.toString(),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Gold,
                        modifier = Modifier
                            .align(Center)
                    )
                }
                Text(
                    text = coinModel.symbol,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(start = 5.dp)
                )

            }

        }

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

   Divider( color = LightGray)
}