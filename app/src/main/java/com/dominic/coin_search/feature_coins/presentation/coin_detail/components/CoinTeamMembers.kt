package com.dominic.coin_search.feature_coins.presentation.coin_detail.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.feature_coins.data.dto.Team
import com.dominic.coin_search.feature_coins.domain.models.CoinInformationModel
import com.dominic.coin_search.ui.theme.Black800

@Composable
fun CoinTeamMembers(modifier: Modifier, coinInformationModel: CoinInformationModel, context: Context) {
    if (coinInformationModel.team.isNotEmpty()) {
        Column(modifier = modifier) {
            Text(
                text = "Team Member/s",
                color = Color.White,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp)


            coinInformationModel.team.forEach { member ->
                TeamListItem(
                    teamMember = member, modifier = Modifier
                        .clickable {
                            openBrowser(
                                context = context,
                                searchItem = "${member.name} ${member.position} of ${coinInformationModel.name}")
                        }
                        .fillMaxWidth()
                        .padding(top = 10.dp, bottom = 10.dp, end = 10.dp))
                Divider(color = Black800,  )
            }

        }
    }

}

@Preview
@Composable
fun CoinTeamMembersPreview() {
    CoinTeamMembers(modifier = Modifier.padding(start = 15.dp, end =  15.dp, top = 12.dp),
        coinInformationModel = CoinInformationModel(
            team = listOf(
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"),
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"),
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"),
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"),
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"),
                Team(
                    id = "001",
                    name = "John Doe",
                    position = "Developer"))), LocalContext.current)
}