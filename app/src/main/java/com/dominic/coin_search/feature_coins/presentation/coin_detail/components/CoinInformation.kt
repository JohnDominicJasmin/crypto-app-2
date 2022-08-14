package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dominic.coin_search.ui.theme.Black920
import com.dominic.coin_search.ui.theme.DarkGray
import com.dominic.coin_search.ui.theme.GreenBlue600

@Composable
fun CoinInformation(
    modifier: Modifier = Modifier,
    volume: String,
    marketCap: String,
    availableSupply: String,
    totalSupply: String,
    rank: String
) {
    Column(modifier = modifier) {

        CoinInfoRow(value = rank, title = "Rank")
        CoinInfoRow(value = marketCap, title = "Market cap")
        CoinInfoRow(value = volume, title = "Volume")
        CoinInfoRow(value = availableSupply, title = "Available supply")
        CoinInfoRow(value = totalSupply, title = "Total supply")

    }
}

@Composable
fun CoinInfoRow(
    value: String,
    title: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            style = MaterialTheme.typography.body2,
        )



        Text(
            text = value,
            color = GreenBlue600,
            style = MaterialTheme.typography.body2,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold
        )

    }
}

@Preview
@Composable
fun CoinInformationPreview() {
    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()) {
        CoinInformation(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 12.dp)
                .background(
                    color = Black920,
                    shape = RoundedCornerShape(18.dp))
                .padding(horizontal = 15.dp, vertical = 10.dp)

            ,
            rank = "1",
            volume = "$9999999",
            marketCap = "$9999999",
            availableSupply = "9999999 BTC",
            totalSupply = "9999999 BTC"
        )
    }
}