package com.dominic.coin_search

import com.dominic.coin_search.feature_coins.presentation.coins_screen.formatToShortNumber
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.DecimalFormat
import java.text.SimpleDateFormat
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
    fun millis24HoursTest(){


        val millis = 1659036600
        val date = Date(millis.toLong() * 1000)
        val dateFormat = SimpleDateFormat("HH:mm")
        assertEquals("03:30", dateFormat.format(date))

    }

    @Test
    fun millis1WeekTest(){
        val millis = 1658519280
        val date = Date(millis.toLong() * 1000)
        val dateFormat = SimpleDateFormat("EEE")
        assertEquals("Sat", dateFormat.format(date))
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