package com.dominic.coin_search.feature_coins.presentation.coins_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.R
import com.dominic.coin_search.ui.theme.*

@Composable
fun SearchBar(
    hint: String = "Search",
    modifier: Modifier,
    searchQuery: String,
    hasFocusRequest: Boolean = false,
    hasTrailingIcon: Boolean = false,
    onValueChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var isHintDisplayed by rememberSaveable { mutableStateOf(searchQuery.isEmpty()) }


    LaunchedEffect(key1 = isHintDisplayed) {
        if (isHintDisplayed && hasFocusRequest) {
            focusRequester.requestFocus()
        }
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = Black850, shape = RoundedCornerShape(12.dp))
            .background(Black920)
    ) {


        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "Search",
            tint = Black450,
            modifier = Modifier
                .size(20.dp)
                .padding(start = 6.dp),
        )
        Box(
            modifier = Modifier
                .padding(vertical = 9.dp, horizontal = 2.dp)
                .fillMaxWidth(0.85f)) {

            BasicTextField(
                value = searchQuery,
                onValueChange = {
                    isHintDisplayed = it.isEmpty()
                    onValueChange(it)
                },
                maxLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search,
                    autoCorrect = false,
                ),
                textStyle = TextStyle(color = Color.White, fontSize = 14.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                cursorBrush = SolidColor(Color.White)
            )

            if (isHintDisplayed) {
                Text(
                    text = hint,
                    color = Black450,
                    modifier = Modifier, fontSize = 14.sp
                )
            }

        }

        if (hasTrailingIcon) {
            IconButton(onClick = onTrailingIconClick, modifier = Modifier.size(16.dp)) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Search",
                    tint = Black450,
                    modifier = Modifier
                        .size(16.dp)
                        .padding(end = 3.dp)
                )
            }
        }
    }
}


