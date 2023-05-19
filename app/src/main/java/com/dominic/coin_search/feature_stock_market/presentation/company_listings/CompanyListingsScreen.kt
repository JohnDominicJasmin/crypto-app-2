package com.dominic.coin_search.feature_stock_market.presentation.company_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dominic.coin_search.navigation.Screens
import com.dominic.coin_search.navigation.navigateScreen
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.presentation.company_listings.CompanyItem
import com.plcoding.stockmarketapp.presentation.company_listings.CompanyListingsState
import com.plcoding.stockmarketapp.presentation.company_listings.CompanyListingsViewModel

@Composable
fun CompanyListingsScreen(
    navController: NavController,
    viewModel: CompanyListingsViewModel = hiltViewModel()
) {
    val state = viewModel.state

    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = state.isRefreshing
    )

    CompanyListContent(
        state = state,
        swipeRefreshState = swipeRefreshState,
        onRefresh = {
            viewModel.onEvent(event = CompanyListingsEvent.Refresh)
        },
        onSearchValueChange = {
            viewModel.onEvent(event = CompanyListingsEvent.OnSearchQueryChange(it))
        },
        onClickCompanyItem = {
            navController.navigateScreen(it)
        }
    )

}

@Preview
@Composable
fun PreviewCompanyListingScreen() {
    DashCoinTheme(darkTheme = true) {
        CompanyListContent(
            state = CompanyListingsState(
                companies = listOf(
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ),
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ),
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ),
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ),
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ), CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    ),
                    CompanyListing(
                        name = "Google",
                        symbol = "Ggl",
                        exchange = "Sample Exchange"
                    )


                )
            ),
            swipeRefreshState = SwipeRefreshState(isRefreshing = false),
            onRefresh = {},
            onSearchValueChange = {},
            onClickCompanyItem = {}
        )
    }
}

@Composable
fun CompanyListContent(
    state: CompanyListingsState,
    swipeRefreshState: SwipeRefreshState,
    onRefresh: () -> Unit,
    onSearchValueChange: (String) -> Unit,
    onClickCompanyItem: (symbol: String) -> Unit) {

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = {
                    onSearchValueChange(it)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text(text = "Search...")
                },
                maxLines = 1,
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colors.primary
                )

            )
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = onRefresh
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.companies.size) { i ->
                        val company = state.companies[i]
                        CompanyItem(
                            company = company,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onClickCompanyItem("${Screens.CompanyInfoScreen.route}/${company.symbol}")
                                }
                                .padding(16.dp)
                        )
                        if (i < state.companies.size) {
                            Divider(
                                modifier = Modifier.padding(
                                    horizontal = 16.dp
                                ))
                        }
                    }
                }
            }
        }
    }
}