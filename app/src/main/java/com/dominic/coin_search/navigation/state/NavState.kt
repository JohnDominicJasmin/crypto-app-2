package com.dominic.coin_search.navigation.state

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class NavState(
    val navigationStartingDestination:String? = null
) : Parcelable
