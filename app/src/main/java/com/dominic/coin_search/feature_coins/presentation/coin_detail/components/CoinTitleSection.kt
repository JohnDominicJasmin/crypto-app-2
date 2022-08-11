package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.feature_coins.domain.models.CoinInformationModel
import com.dominic.coin_search.ui.theme.DarkGray

@Composable
fun CoinTitleSection(modifier: Modifier, coinInfo: CoinInformationModel) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "${coinInfo.name} (${coinInfo.symbol})",
            modifier = Modifier.weight(8f),
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp)


        Status(
            isActive = coinInfo.isActive,
            modifier = Modifier.align(Alignment.CenterVertically))


    }
}


@Preview(showSystemUi = true, backgroundColor = 161520)
@Composable
fun CoinTitlePreview() {

    Box(modifier = Modifier
        .background(DarkGray)
        .fillMaxSize()) {
        CoinTitleSection(
            coinInfo = CoinInformationModel(
                name = "Bitcoin",
                symbol = "BTC",
                isActive = true), modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp))
    }
}
