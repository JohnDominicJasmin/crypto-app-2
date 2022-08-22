package com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.feature_coins.presentation.coin_detail.components.openBrowser
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.SearchBar
import com.dominic.coin_search.feature_coins.presentation.coins_screen.components.TopBar
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteAddButton
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteCoinItem
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteNewsItem
import com.dominic.coin_search.feature_favorite_list.presentation.favorite_list_screen.components.FavoriteToggleButton
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.navigation.navigateScreen
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun FavoriteListScreen(
    innerPaddingValues: PaddingValues,
    favoritesViewModel: FavoritesViewModel = hiltViewModel(),
    navController: NavController?
) {
    val favoriteState by favoritesViewModel.state.collectAsState()
    val context = LocalContext.current
    val (isCoinSelected, onCoinSelected) = rememberSaveable { mutableStateOf(true) }
    val (searchBarVisible, onSearchIconToggle) = rememberSaveable { mutableStateOf(false) }
    val (searchQuery, onSearchQuery) = rememberSaveable { mutableStateOf("") }
    LaunchedEffect(true) {

        favoritesViewModel.eventFlow.collectLatest { savedListEvent ->
            when (savedListEvent) {
                is FavoriteListUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, savedListEvent.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    val filteredCoins = remember(searchQuery, favoriteState.coins) {
        favoriteState.coins.filter {
            it.name.contains(searchQuery.trim(), ignoreCase = true) ||
            it.id.contains(searchQuery.trim(), ignoreCase = true) ||
            it.symbol.contains(searchQuery.trim(), ignoreCase = true)
        }
    }
    val filteredNews = remember(searchQuery, favoriteState.news) {
        favoriteState.news.filter {
            it.title.contains(searchQuery.trim(), ignoreCase = true) ||
            it.source.contains(searchQuery.trim(), ignoreCase = true)
        }
    }



    Scaffold(
        topBar = {
            TopBar(
                currencyValue = null,
                modifier = Modifier
                    .height(55.dp)
                    .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                    .fillMaxWidth(),
                onSearchClick = {
                    onSearchIconToggle(!searchBarVisible)
                })
        }) {


        Box(
            modifier = Modifier
                .padding(innerPaddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AnimatedVisibility(visible = searchBarVisible) {
                    SearchBar(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(vertical = 4.dp),
                        hasTrailingIcon = true,
                        hasFocusRequest = true,
                        searchQuery = searchQuery,
                        onValueChange = { value ->
                            onSearchQuery(value)
                        }, onTrailingIconClick = {
                            onSearchIconToggle(!searchBarVisible)
                            onSearchQuery("")
                        }
                    )

                }

                LazyColumn(
                    modifier = Modifier
                        .background(DarkGray)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    item {
                        FavoriteToggleButton(
                            modifier = Modifier.padding(top = 10.dp, bottom = 7.dp),
                            isCoinSelected = isCoinSelected,
                            onCoinsButtonClick = { onCoinSelected(it) })
                    }
                    if (isCoinSelected) {
                        items(filteredCoins, key = { it.id }) { coin ->
                            FavoriteCoinItem(
                                modifier = Modifier.animateItemPlacement(
                                    tween(durationMillis = 500)
                                ),
                                coin = coin,
                                onItemClick = {
                                    navController?.navigate(Screens.CoinDetailScreen.route + "/${coin.id}")
                                },
                                onDeleteClick = {
                                    favoritesViewModel.onEvent(FavoriteListEvent.DeleteCoin(coin))
                                }
                            )
                        }
                    } else {
                        items(filteredNews, key = { it.id }) { news ->
                            FavoriteNewsItem(
                                modifier = Modifier.animateItemPlacement(
                                    tween(durationMillis = 500)
                                ),
                                newsModel = news,
                                onItemClick = {
                                    openBrowser(context, news.link!!)
                                },
                                onDeleteClick = {
                                    favoritesViewModel.onEvent(FavoriteListEvent.DeleteNews(news))
                                }
                            )
                        }

                    }
                }
            }
            if (favoriteState.coins.isEmpty() && isCoinSelected) {
                EmptyItemsPlaceholder(
                    modifier = Modifier.align(Alignment.Center),
                    textPlaceholder = "No saved coins to display",
                    onClickButton = {
                        navController?.navigateScreen(Screens.CoinsScreen.route)
                    })
            }

            if (favoriteState.news.isEmpty() && !isCoinSelected) {
                EmptyItemsPlaceholder(
                    modifier = Modifier.align(Alignment.Center),
                    textPlaceholder = "No saved news to display",
                    onClickButton = {
                        navController?.navigateScreen(Screens.CoinsNews.route)
                    })

            }


        }
    }
}

@Composable
private fun EmptyItemsPlaceholder(
    modifier: Modifier,
    textPlaceholder: String,
    onClickButton: () -> Unit) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = textPlaceholder,
            color = Black450,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        )
        FavoriteAddButton(
            modifier = Modifier.fillMaxWidth(0.75f),
            onClick = onClickButton)
    }
}
