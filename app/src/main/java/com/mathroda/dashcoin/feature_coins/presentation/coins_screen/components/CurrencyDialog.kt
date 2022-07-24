package com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import com.mathroda.dashcoin.feature_coins.data.dto.FiatCurrencyItem
import com.mathroda.dashcoin.ui.theme.Black920
import java.util.*
import com.mathroda.dashcoin.feature_coins.domain.models.CoinCurrencyPreference


@Composable
fun CoinCurrencyScreen(
    currencies: List<FiatCurrencyItem>,
    onDismissRequest: (CoinCurrencyPreference? ) -> Unit
) {

        Dialog(onDismissRequest = { onDismissRequest(null) }) {

            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxHeight(0.97f),
                elevation = 10.dp,
                backgroundColor = Black920
            ) {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    SearchBar(
                        hint = "Search",
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 20.dp, start = 12.dp, end = 12.dp),
                        searchQuery = "",
                        onValueChange = {
                        //todo do some searching
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 3.dp, end = 3.dp)
                            .weight(0.9f)
                            .clipToBounds()
                    ) {






                        items(items = currencies) { currency ->
                            CurrencyItem(
                                imageModel = currency.imageUrl,
                                currency = currency.name,
                                currencyName = toCountryName(currency.name),
                                currencySymbol = currency.symbol,
                                onClickItem = { selectedCurrency ->
                                    onDismissRequest(selectedCurrency)
                                })
                        }

                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(vertical = 3.5.dp, horizontal = 3.dp)) {
                        Spacer(modifier = Modifier.weight(0.8f))
                        TextButton(
                            onClick = { onDismissRequest(null) },
                            colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Transparent)) {
                            Text(
                                text = "CANCEL",
                                color = Color.White,
                                letterSpacing = 1.sp,
                                fontSize = 12.sp)
                        }
                    }


                }



        }


    }
}


private fun toCountryName(currencyCode:String): String {
    val currency = Currency.getInstance(currencyCode)
    val result = currency.getDisplayName(Locale.getDefault())
    return result.replace("Piso", "Peso")
}