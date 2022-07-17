package com.mathroda.dashcoin.feature_coins.presentation.coins_news

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.TextFieldValue
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
import com.mathroda.dashcoin.ui.theme.CustomGreen
import com.mathroda.dashcoin.ui.theme.DarkGray
import kotlinx.coroutines.flow.collectLatest

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




    LaunchedEffect(key1 = true ){
        newsViewModel.eventFlow.collectLatest { event ->
            when(event){
                is NewsUiEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Box(
        modifier = modifier
            .background(DarkGray)
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Column {
            TopBar(title = "Trending News")
            SearchBar(
                hint = "Search...",
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
                        }, key = {it.title}) { news ->
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
                color = CustomGreen
            )
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
