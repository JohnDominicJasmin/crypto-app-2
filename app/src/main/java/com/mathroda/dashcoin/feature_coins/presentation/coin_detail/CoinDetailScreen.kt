package com.mathroda.dashcoin.feature_coins.presentation.coin_detail

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.components.*
import com.mathroda.dashcoin.feature_coins.presentation.coin_detail.utils.CoinDetailEvent
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import com.mathroda.dashcoin.ui.theme.LighterGray
import com.mathroda.dashcoin.ui.theme.Twitter
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListEvent
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListUiEvent
import com.mathroda.dashcoin.feature_favorite_list.presentation.favorite_list_screen.FavoriteListViewModel
import com.mathroda.dashcoin.feature_no_internet.presentation.NoInternetScreen
import com.mathroda.dashcoin.navigation.Screens
import com.mathroda.dashcoin.navigation.navigateScreen
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

@Composable
fun CoinDetailScreen(
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    favoriteListViewModel: FavoriteListViewModel = hiltViewModel(),
    navController: NavController?
) {

    val coinState = coinDetailViewModel.state

    val scaffoldState = rememberScaffoldState()
    val context = LocalContext.current



    LaunchedEffect(true) {

        favoriteListViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoriteListUiEvent.ShowSnackbar -> {

                    val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                        message = savedListEvent.message,
                        actionLabel = savedListEvent.buttonAction
                    )
                    when (snackbarResult) {
                        SnackbarResult.ActionPerformed -> {
                            if (savedListEvent.buttonAction == "See list") {
                                navController?.navigateScreen(Screens.FavoriteListScreen.route)
                                return@collectLatest
                            }

                            favoriteListViewModel.onEvent(event = FavoriteListEvent.RestoreDeletedCoin)
                            coinDetailViewModel.onEvent(event = CoinDetailEvent.ToggleFavoriteCoin)
                        }
                        else -> {}
                    }
                }
            }
        }
    }



    Scaffold(
        modifier = Modifier,
        scaffoldState = scaffoldState
    ) {

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
                   .padding(it)
           ) {
               item {

                   TopBarCoinDetail(
                       coinSymbol = coin.symbol,
                       icon = coin.icon,
                       backButtonOnClick = { navController?.popBackStack() },
                       isFavorite = coinState.isFavorite,
                       favoriteButtonOnClick = {
                           if (!coinState.isFavorite){
                               favoriteListViewModel.onEvent(FavoriteListEvent.AddCoin(coin))
                           }else{
                               favoriteListViewModel.onEvent(FavoriteListEvent.DeleteCoin(coin))
                           }
                           coinDetailViewModel.onEvent(event = CoinDetailEvent.ToggleFavoriteCoin)

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


        if(!coinState.hasInternet){
            NoInternetScreen(onTryButtonClick = {
                if(ConnectionStatus.hasInternetConnection(context)){
                    coinDetailViewModel.onEvent(event = CoinDetailEvent.CloseNoInternetDisplay)
                }
            })
        }

        if(coinState.errorMessage.isNotEmpty()) {
            Text(
                text = coinState.errorMessage,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
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


