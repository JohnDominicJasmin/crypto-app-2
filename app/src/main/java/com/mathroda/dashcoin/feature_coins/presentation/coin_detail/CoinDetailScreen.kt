package com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailUiEvent
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.CoinDetailViewModel
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import com.mathroda.dashcoin.ui.theme.LighterGray
import com.mathroda.dashcoin.ui.theme.Twitter
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.SavedListEvent
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.SavedListUiEvent
import com.mathroda.dashcoin.feature_watch_list.presentation.saved_list_screen.SavedListViewModel
import kotlinx.coroutines.flow.collectLatest
import java.text.NumberFormat
import java.util.*

@Composable
fun CoinDetailScreen(
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    savedListViewModel: SavedListViewModel = hiltViewModel(),
    navController: NavController
) {

    val coinState = coinDetailViewModel.state
    val savedListState = savedListViewModel.state
    val context = LocalContext.current



    LaunchedEffect(key1 = true){
        coinDetailViewModel.eventFlow.collectLatest { coinDetailEvent ->
            when(coinDetailEvent){
                is CoinDetailUiEvent.ShowNoInternetScreen -> {
                    //todo: show no internet screen here
                }

                is CoinDetailUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, coinDetailEvent.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        savedListViewModel.eventFlow.collectLatest { savedListEvent ->
            when(savedListEvent){
                is SavedListUiEvent.ShowSnackbar -> {

                }
            }
        }
    }


    if(savedListState.snackbarVisible){
        Snackbar(

            action = {
                TextButton(onClick = {}) {
                    Text("MyAction")
                }
            },
            modifier = Modifier.padding(8.dp)
        ) { Text(text = "This is a snackbar!") }
    }
    Box(
        modifier = Modifier
            .background(DarkGray)
            .fillMaxSize()
            .padding(12.dp)
    ) {
       coinState.coinDetailModel?.let { coin ->
           LazyColumn(
               modifier = Modifier
                   .fillMaxSize()
           ) {
               item {
                   var isFavorite by rememberSaveable { mutableStateOf(false) }
                   TopBarCoinDetail(
                       coinSymbol = coin.symbol,
                       icon = coin.icon,
                       navController = navController,
                       isFavorite = isFavorite,
                       onCLick = {
                           isFavorite = !isFavorite
                           if (isFavorite){
                               savedListViewModel.onEvent(SavedListEvent.AddCoin(coin))
                           }
                       }
                   )

                   CoinDetailSection(
                       price = coin.price,
                       priceChange = coin.priceChange1d
                   )

                   Chart(
                       chartModel = coinState.chartModel,
                       oneDayChange = coin.priceChange1d,
                       context = LocalContext.current
                   )

                   CoinInformation(
                       modifier = Modifier
                           .fillMaxWidth()
                           .background(
                               color = LighterGray,
                               shape = RoundedCornerShape(25.dp)
                           )
                           .padding(start = 16.dp, top = 16.dp, end = 16.dp),
                       rank = "${coin.rank}",
                       volume = numbersToCurrency(coin.volume.toInt())!!,
                       marketCap = numbersToCurrency(coin.marketCap.toInt())!!,
                       availableSupply = "${numbersToFormat(coin.availableSupply.toInt())} ${coin.symbol}" ,
                       totalSupply = "${numbersToFormat(coin.totalSupply.toInt())} ${coin.symbol}"
                   )

                   val uriHandler = LocalUriHandler.current
                   Row (
                       horizontalArrangement = Arrangement.Center
                           ){
                       LinkButton(
                           title = "Twitter",
                           modifier = Modifier
                               .padding(start = 20.dp, bottom = 20.dp, top = 20.dp)
                               .clip(RoundedCornerShape(35.dp))
                               .height(45.dp)
                               .background(Twitter)
                               .weight(1f)
                               .clickable {
                                   uriHandler.openUri(coin.twitterUrl!!)
                               }
                       )

                       LinkButton(
                           title = "Website",
                           modifier = Modifier
                               .padding(start = 20.dp, bottom = 20.dp, top = 20.dp)
                               .clip(RoundedCornerShape(35.dp))
                               .height(45.dp)
                               .background(LighterGray)
                               .weight(1f)
                               .clickable {
                                   uriHandler.openUri(coin.websiteUrl)
                               }
                       )
                   }
               }
           }
       }

        if (coinState.isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .align(Alignment.Center),
                color = CustomGreen
            )
        }
        //todo: investigate this one

            Text(
                text = "ERROR SAMPLE",
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
    }
}



fun numbersToCurrency(number: Int): String? {
    val numberFormat = NumberFormat.getCurrencyInstance()
    numberFormat.currency = Currency.getInstance("USD")
    return numberFormat.format(number)
}

fun numbersToFormat(number: Int): String? {
    val numberFormat = NumberFormat.getNumberInstance()
    return numberFormat.format(number)
}


