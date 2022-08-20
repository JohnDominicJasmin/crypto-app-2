package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dominic.coin_search.R
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinDetailModel
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.ui.theme.Black920
import com.dominic.coin_search.ui.theme.GreenBlue600
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FavoriteCoinItem(modifier: Modifier, coin: CoinDetailModel, onItemClick: () -> Unit, onDeleteClick: () -> Unit) {

    val (isSavedCoin, onIconSaveClick) = rememberSaveable {
        mutableStateOf(true)
    }

    val scope = rememberCoroutineScope()
    AnimatedVisibility(
        visible = isSavedCoin,
        enter = fadeIn(initialAlpha = 0.4f)) {


        Card(
            shape = RoundedCornerShape(16.dp),
            backgroundColor = Black920,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp, vertical = 5.dp)
                .wrapContentHeight(), onClick = onItemClick) {

            Column(modifier = Modifier.padding(all = 12.dp, )) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row(
                        modifier = Modifier.weight(0.9f),
                        verticalAlignment = Alignment.CenterVertically) {

                        AsyncImage(
                            model = coin.icon,
                            contentDescription = "${coin.name} Image",
                            modifier = Modifier
                                .size(40.dp))


                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    SpanStyle(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp)) {
                                    append("${coin.name}\n")
                                }

                                withStyle(
                                    SpanStyle(
                                        color = Black450,
                                        fontWeight = FontWeight(200),
                                        fontSize = 16.sp)) {
                                    append(coin.symbol.uppercase(Locale.getDefault()))
                                }
                            },
                            textAlign = TextAlign.Start,
                            modifier = Modifier.padding(start = 12.dp))

                    }


                        Icon(
                            painter = painterResource(id = if (isSavedCoin) R.drawable.ic_baseline_bookmark_filled else R.drawable.ic_baseline_bookmark_border_24),
                            contentDescription = "Save Icons",
                            tint = if (isSavedCoin) GreenBlue600 else Color.White,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    onIconSaveClick(!isSavedCoin)
                                    delay(300)
                                    onDeleteClick()
                                }
                            }
                        )
                }


                MarketStatusBar(
                    modifier = Modifier
                        .padding(top = 7.dp)
                        .fillMaxWidth(),
                    marketStatus1d = coin.priceChange1d,
                    marketStatus1w = coin.priceChange1w,
                    marketStatus1h = coin.priceChange1h,
                )


            }
        }
    }
}


