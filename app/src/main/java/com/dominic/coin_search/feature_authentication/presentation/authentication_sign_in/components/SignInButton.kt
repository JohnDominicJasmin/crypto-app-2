package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.feature_authentication.presentation.common.AuthenticationConstraintsItem

@Composable
fun SignInButton(enabled: Boolean, onClickSignInButton: () -> Unit) {

    Box(
        modifier = Modifier
            .layoutId(layoutId = AuthenticationConstraintsItem.SignInButton.layoutId)
            .wrapContentSize(),
        contentAlignment = Alignment.Center) {
        Button(

            onClick = onClickSignInButton,
            enabled = enabled,
            modifier = Modifier
                .height(45.dp)
                .width(220.dp)
                .shadow(7.dp, shape = RoundedCornerShape(12.dp), clip = true),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                disabledBackgroundColor = MaterialTheme.colors.primary,
                disabledContentColor = MaterialTheme.colors.onPrimary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Sign In", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }

}