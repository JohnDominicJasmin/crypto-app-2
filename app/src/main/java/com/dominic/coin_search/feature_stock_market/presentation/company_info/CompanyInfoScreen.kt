package com.dominic.coin_search.feature_stock_market.presentation.company_info

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dominic.coin_search.ui.theme.DashCoinTheme
import com.plcoding.stockmarketapp.domain.model.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.presentation.company_info.CompanyInfoState
import java.time.LocalDateTime

@Composable
fun CompanyInfoScreen(
    viewModel: CompanyInfoViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    CompanyInfoContent(state = state)
}


@Preview
@Composable
fun CompanyInfoPreview() {

    DashCoinTheme(darkTheme = true) {
        CompanyInfoContent(
            state = CompanyInfoState(
                company = CompanyInfo(
                    symbol = "Apple",
                    description = "Lorem ipsum dolor sit amet consectetur adipisicing elit. Maxime mollitia,\n" +
                                  "molestiae quas vel sint commodi repudiandae consequuntur voluptatum laborum\n" +
                                  "numquam blanditiis harum quisquam eius sed odit fugiat iusto fuga praesentium\n" +
                                  "optio, eaque rerum! Provident similique accusantium nemo autem.",
                    name = "Techno",
                    country = "USA",
                    industry = "Tech"
                ),
                stockInfos = listOf(
                    IntradayInfo(
                        date = LocalDateTime.of(2021, 1, 1, 1, 1),
                        close = 100.0,
                    ),
                    IntradayInfo(
                        date = LocalDateTime.of(2021, 2, 2, 1, 1),
                        close = 90.0
                    ),
                    IntradayInfo(
                        date = LocalDateTime.of(2021, 3, 2, 1, 1),
                        close = 80.0
                    ),
                    IntradayInfo(
                        date = LocalDateTime.of(2021, 4, 2, 1, 1),
                        close = 70.0
                    ),
                ),
                isLoading = false,
                error = null
            ))
    }
}

@Composable
fun CompanyInfoContent(state: CompanyInfoState) {

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {

        if (state.error == null) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 20.dp)) {
                state.company?.let { company ->

                    Text(
                        text = company.name.takeIf { it.isNotEmpty() } ?: "Company name is unavailable.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = company.symbol.takeIf { it.isNotEmpty() } ?: "Company Symbol is unavailable.",
                        fontStyle = FontStyle.Italic,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if(company.industry.isEmpty()) "Industry is unavailable" else "Industry: ${company.industry}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if(company.country.isEmpty()) "Country is unavailable" else "Country: ${company.country}",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth(),
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onBackground
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = company.description,
                        fontSize = 12.sp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.onBackground
                    )

                    if (state.stockInfos.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Market Summary", color = MaterialTheme.colors.onBackground)
                        Spacer(modifier = Modifier.height(32.dp))
                        StockChart(
                            infos = state.stockInfos,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .align(CenterHorizontally)
                        )
                    }



                }
            }
        }


        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Center
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else if (state.error != null) {
                Text(
                    text = state.error,
                    color = MaterialTheme.colors.error
                )
            }
        }


    }


}