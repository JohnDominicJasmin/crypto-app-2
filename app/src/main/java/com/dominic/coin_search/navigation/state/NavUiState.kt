package com.dominic.coin_search.navigation.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class NavUiState(
    val internetAvailable: Boolean = false,
    val isNavigating: Boolean = false,
    val photoUrl: String = "",
    val name: String = "",
):Parcelable
