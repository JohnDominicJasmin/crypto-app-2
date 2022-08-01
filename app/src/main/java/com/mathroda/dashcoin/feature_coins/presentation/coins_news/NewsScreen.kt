package com.mathroda.dashcoin.feature_coins.presentation.coins_news

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mathroda.dashcoin.core.util.ConnectionStatus
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.SearchBar
import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.components.TopBar
import com.mathroda.dashcoin.feature_coins.presentation.coins_news.components.NewsCard
import com.mathroda.dashcoin.feature_no_internet.presentation.NoInternetScreen
import com.mathroda.dashcoin.ui.theme.Green800
import com.mathroda.dashcoin.ui.theme.DarkGray

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@ExperimentalMaterialApi
@Composable
fun NewsScreen(
    modifier: Modifier = Modifier,
    newsViewModel: NewsViewModel = hiltViewModel(),
    navController: NavController?
) {
    val state = newsViewModel.state
    val uriHandler = LocalUriHandler.current
    val context = LocalContext.current

    Scaffold(topBar = {
        TopBar(
            currencyValue = null,
            modifier = Modifier
                .height(55.dp)
                .padding(bottom = 5.dp, top = 14.dp, start = 15.dp, end = 5.dp)
                .fillMaxWidth(),
            onCurrencyClick = {
            }, onSearchClick = {

            })
    }) {

        Box(
            modifier = modifier
                .background(DarkGray)
                .fillMaxSize()
        ) {
            Column {

                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth(),
                    searchQuery = state.searchQuery,
                    onValueChange = {
                        newsViewModel.onEvent(event = NewsEvent.EnteredSearchQuery(it))
                    }
                )

                Row(
                    modifier = Modifier.padding(12.dp)
                ) {
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing = state.isRefreshing),
                        onRefresh = { newsViewModel.onEvent(event = NewsEvent.RefreshNews) }) {

                        LazyColumn {

                            items(state.trendingNews.filter {
                                it.title.contains(state.searchQuery.trim(), ignoreCase = true) ||
                                it.description.contains(state.searchQuery.trim(), ignoreCase = true)
                            }, key = { it.title }) { news ->
                                NewsCard(
                                    newsThumb = news.imgURL,
                                    title = news.title,
                                    onClick = {
                                        uriHandler.openUri(news.link)
                                    }
                                )
                                Spacer(Modifier.height(15.dp))
                            }
                        }
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = Green800
                )
            }

            if (!state.hasInternet) {
                NoInternetScreen(onTryButtonClick = {
                    if (ConnectionStatus.hasInternetConnection(context)) {
                        newsViewModel.onEvent(event = NewsEvent.CloseNoInternetDisplay)
                        newsViewModel.onEvent(event = NewsEvent.RefreshNews)
                    }
                })
            }

            if (state.errorMessage.isNotEmpty()) {
                Text(
                    text = state.errorMessage,
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
