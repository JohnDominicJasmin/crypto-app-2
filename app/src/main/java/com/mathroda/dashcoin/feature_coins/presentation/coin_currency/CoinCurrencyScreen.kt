package com.mathroda.dashcoin.feature_coins.presentation.coin_currency

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
import com.mathroda.dashcoin.feature_coins.presentation.coin_currency.components.CurrencyItem
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.SearchBar
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.ui.platform.LocalContext
import com.mathroda.dashcoin.feature_coins.data.dto.FiatCurrencyItem
import com.mathroda.dashcoin.ui.theme.Black920
import java.util.*
import com.mathroda.dashcoin.R




@Composable
fun CoinCurrencyScreen(
    currencies: List<FiatCurrencyItem>,
    onDismissRequest: () -> Unit
) {

    val context = LocalContext.current


        Dialog(onDismissRequest = onDismissRequest) {

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
                        //todo
                        }
                    )
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 20.dp, start = 3.dp, end = 3.dp)
                            .weight(0.9f)
                            .clipToBounds()
                    ) {


                        item{
                            CurrencyItem(imageModel = R.drawable.ic_bitcoin, currency = "BTC", currencyName = "Bitcoin")
                            CurrencyItem(imageModel = R.drawable.ic_ethereum, currency = "ETH", currencyName = "Ethereum")
                            Divider(modifier = Modifier.fillMaxWidth())
                            Spacer(modifier = Modifier.padding(top = 12.dp))
                        }


                        items(items = currencies) { currency ->
                            CurrencyItem(imageModel = currency.imageUrl, currency = currency.name, currencyName = toCountryName(currency.name))
                        }

                    }

                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.padding(vertical = 3.5.dp, horizontal = 3.dp)) {
                        Spacer(modifier = Modifier.weight(0.8f))
                        TextButton(
                            onClick =  onDismissRequest,
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