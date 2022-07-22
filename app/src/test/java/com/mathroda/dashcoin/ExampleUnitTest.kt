package com.mathroda.dashcoin

import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.formatToShortNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class ExampleUnitTest {
    @Test
    fun decimalFormat() {


       val marketCapUsd = 1123211459238L
       val result =  marketCapUsd.formatToShortNumber()
        assertEquals("1.22T",   result)
    }


    @Test
    fun currencyTest() {
        val currency = Currency.getInstance("AUD")
        val initialResult = currency.getDisplayName(Locale.getDefault())
        val finalResult = initialResult.replace("Piso", "Peso")
        assertEquals("Australian Dollar", finalResult)
    }


    @Test
    fun getAllCurrencies() {


        Locale.getAvailableLocales().distinct().forEach { locale ->
            try {
                val currency = Currency.getInstance(locale)
                if (currency != null) {
                    println(locale.country)
                    println(
                        "key: " + currency.displayName + ", curr: " + currency)


                }
            } catch (e: IllegalArgumentException) {

            }
        }

    }
}