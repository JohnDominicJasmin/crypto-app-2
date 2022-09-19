package com.dominic.coin_search.feature_coins.presentation.coin_currency_screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import com.dominic.coin_search.ui.theme.Black920
import java.util.*
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinCurrencyPreference
import com.dominic.coin_search.feature_coins.domain.models.coin.CoinFiatModel
import com.dominic.coin_search.feature_coins.presentation.coin_currency_screen.components.CurrencyItem
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.SearchBar


@Composable
fun CoinCurrencyScreen(
    coinFiat: CoinFiatModel,
    onDismissRequest: (CoinCurrencyPreference? ) -> Unit
) {

    val (searchQuery, onChangeValueSearch) =  remember{mutableStateOf("")}
    val filteredSearchQuery = remember(searchQuery, coinFiat){
        coinFiat.currencies.filter {
            it.name.contains(searchQuery.trim(), ignoreCase = true) ||
            it.symbol.contains(searchQuery.trim(), ignoreCase = true)||
            toCountryName(it.name).contains(searchQuery.trim(), ignoreCase = true)
        }
    }

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
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        searchQuery = searchQuery,
                        onValueChange = { value ->
                            onChangeValueSearch(value)
                        },
                        hasFocusRequest = false,
                        hasTrailingIcon = true,
                        onTrailingIconClick = {
                            onChangeValueSearch("")
                        }
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 3.dp, end = 3.dp)
                            .weight(0.9f)
                            .clipToBounds(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        items(items = filteredSearchQuery, key = {it.name}) { currency ->
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