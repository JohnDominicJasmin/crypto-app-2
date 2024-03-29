package com.dominic.coin_search.feature_authentication.presentation.authentication_sign_in.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dominic.coin_search.feature_authentication.presentation.common.AuthenticationConstraintsItem
import com.dominic.coin_search.ui.theme.Black450
import com.dominic.coin_search.R
import com.dominic.coin_search.ui.theme.Blue500

@Composable
fun SignInGoogleAndFacebookSection(
    onClickFacebookButton: () -> Unit,
    enabled: Boolean,
    onClickGoogleButton: () -> Unit) {
    Row(
        modifier = Modifier
            .layoutId(AuthenticationConstraintsItem.OtherSignIns.layoutId)
            .wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {

        Button(
            onClick = onClickGoogleButton,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
                contentColor = Black450,
                disabledBackgroundColor = Color.White,
                disabledContentColor = Black450),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp)
                .shadow(10.dp, shape = RoundedCornerShape(15.dp), clip = true)
                .width(150.dp)
                .height(45.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_google),
                    contentDescription = "Google Sign In",
                    tint = Color.Unspecified,
                )


                Text(
                    text = "Google",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp)
            }


        }

        Spacer(modifier = Modifier.width(14.dp))

        Button(
            onClick = onClickFacebookButton,
            enabled = enabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue500,
                disabledBackgroundColor = Blue500,
                contentColor = Color.White,
                disabledContentColor = Color.White),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .padding(top = 15.dp, bottom = 15.dp)
                .shadow(10.dp, shape = RoundedCornerShape(15.dp), clip = true)
                .width(150.dp)
                .height(45.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {


                Icon(
                    painter = painterResource(id = R.drawable.ic_facebook_),
                    contentDescription = "Facebook Sign In",
                    tint = Color.Unspecified
                )

                Text(
                    text = "Facebook",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 14.sp
                )

            }

        }

    }
}