package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mathroda.dashcoin.R
import com.mathroda.dashcoin.core.util.toFormattedPrice
import com.mathroda.dashcoin.feature_coins.domain.models.CoinDetailModel
import com.mathroda.dashcoin.ui.theme.*

@Composable
fun CoinDetailSection(
    modifier: Modifier,
    coinModel: CoinDetailModel,
    chartDate: String,
    chartPrice: String,
    ) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        contentAlignment = Alignment.Center
    ) {


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround,
        ) {

            AsyncImage(
                model = coinModel.icon,
                contentDescription = "Icon",
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Fit,
            )

            Text(
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.h6,
                text = "$${chartPrice.ifEmpty { coinModel.price.toFormattedPrice() }}",
                color = White800,
                modifier = Modifier.padding(top = 5.dp)
            )

            AnimatedVisibility(visible = chartDate.isNotEmpty()) {

                Text(
                    text = chartDate,
                    fontWeight = FontWeight.Normal,
                    color = Black450,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.7.dp,bottom = 5.dp)

                )
            }


            Surface(
                shape = RoundedCornerShape(16.dp),
                color = if(coinModel.priceChange1w < 0) Red20 else Green20,
            modifier = Modifier.padding(top = 8.dp)){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 8.dp)) {
                    Image(
                        painter = painterResource(id = if(coinModel.priceChange1w < 0) R.drawable.ic_arrow_negative else R.drawable.ic_arrow_positive),
                        contentDescription = "Arrow Positive/Negative Indicator",
                        modifier = Modifier
                            .padding(start = 2.dp, end = 8.dp)
                            .size(10.dp)
                    )
                    Text(
                        text = (if(coinModel.priceChange1w < 0) "" else "+") + coinModel.priceChange1w.toString() + "%",
                        style = MaterialTheme.typography.body1,
                        color = if(coinModel.priceChange1w < 0) Red900 else Green800,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}




