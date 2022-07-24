package com.mathroda.dashcoin

import com.mathroda.dashcoin.feature_coins.presentation.coins_screen.formatToShortNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DecimalFormat
import java.util.*


class ExampleUnitTest {
    @Test
    fun shortDecimalFormat() {


       val marketCapUsd = 1123211459238L
       val result =  marketCapUsd.formatToShortNumber()
        assertEquals("1.22T",   result)
    }


    @Test
    fun decimalFormat(){
        val price = 1123211459238L
        val format = DecimalFormat("#,###,###.###")
        print(format.format(price))
    }

    @Test
    fun currencyTest() {

        val currency = Currency.getInstance("EUR")
        val result = currency.symbol
        assertEquals("₱", result)
    }



    @Test
    fun getAllCurrencies() {


        Locale.getAvailableLocales().distinct().forEach { l ->
            try {
                val c = Currency.getInstance(l)
                if (c != null) {
                    System.out.println("key: " + l.getCountry() + ", loc: " + l + ", curr: " + c + " (" + c.getSymbol(l) + ")");


                }
            } catch (e: IllegalArgumentException) {

            }
        }

    }
}