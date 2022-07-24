package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference

@Composable
fun CurrencyItem(
    imageModel: Any,
    currencyName: String,
    currency: String,
    currencySymbol: String,
    onClickItem: (CoinCurrencyPreference) -> Unit) {


    Box(modifier = Modifier.clickable {
        onClickItem(
            CoinCurrencyPreference(
                currency = currency,
                currencySymbol = currencySymbol))
    }) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 13.dp),
            verticalAlignment = Alignment.CenterVertically) {


            when (imageModel) {
                is String -> {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = "Country Flag Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape))
                }
                is Int -> {
                    Image(
                        painter = painterResource(imageModel),
                        contentDescription = "Country Flag Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(CircleShape))

                }
            }
            Spacer(modifier = Modifier.weight(0.6f))

            Text(color = Color.White,
                fontSize = 14.sp,
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append("$currencyName  ")
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                        append(currency)
                    }
                }
            )
        }
    }
}
/*

@Preview
@Composable
fun CurrencyItemPreview() {
CurrencyItem()
}*/
