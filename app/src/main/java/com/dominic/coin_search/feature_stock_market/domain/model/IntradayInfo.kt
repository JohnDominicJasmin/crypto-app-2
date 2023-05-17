package com.plcoding.stockmarketapp.domain.model

import android.os.Parcelable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
@Stable
@Immutable
data class IntradayInfo(
    val date: LocalDateTime,
    val close: Double
):Parcelable
