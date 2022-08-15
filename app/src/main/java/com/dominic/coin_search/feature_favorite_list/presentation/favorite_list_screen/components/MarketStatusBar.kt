package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components

import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.ui.theme.Green800
import com.dominic.coin_search.ui.theme.Red900
import com.dominic.coin_search.R

@Composable
fun MarketStatusBar(
    modifier: Modifier = Modifier,
    marketStatus1h: Double,
    marketStatus1d: Double,
    marketStatus1w: Double,

    ) {

    Row(
        modifier = modifier,
    ) {

        MarketStatusItem(
            title = "1h",
            marketStatus = marketStatus1h,
            modifier = Modifier.weight(1f)
        )

        MarketStatusItem(
            title = "1d",
            marketStatus = marketStatus1d,
            modifier = Modifier.weight(1f)
        )

        MarketStatusItem(
            title = "1w",
            marketStatus = marketStatus1w,
            modifier = Modifier.weight(1f)
        )

    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MarketStatusItem(
    modifier: Modifier = Modifier,
    title: String,
    marketStatus: Double,
) {
    Column(
        modifier = modifier
            .padding(2.dp)
    ) {
        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 0.dp)
        ) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.body1,
                color = Color.White,
            )
        }

        Row(
            verticalAlignment = CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = if (marketStatus < 0) painterResource(id = R.drawable.ic_arrow_negative)
                else painterResource(id = R.drawable.ic_arrow_positive),
                contentDescription = null,
                modifier = Modifier
                    .size(12.dp)
                    .padding(end = 4.dp)
            )

            AnimatedContent(targetState = marketStatus,  transitionSpec = {

                if (targetState > initialState) {
                    slideInVertically { height -> height } + fadeIn() with
                            slideOutVertically { height -> -height } + fadeOut()
                }else {
                    slideInVertically { height -> -height } + fadeIn() with
                            slideOutVertically { height -> height } + fadeOut()
                }.using(
                    SizeTransform(clip = false)
                )
            }) { status ->

            Text(
                text = "$status%",
                style = MaterialTheme.typography.body2,
                color = if (marketStatus < 0) Red900 else Green800,
                modifier = Modifier
            )
            }

        }

    }
}