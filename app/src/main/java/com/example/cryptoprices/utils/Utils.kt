package com.example.cryptoprices.utils

import android.icu.text.NumberFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Currency
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.INR
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.INR_TO_SEK
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.INR_TO_USD
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.USD
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.USDT
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl.Companion.USD_TO_SEK
import java.math.BigDecimal
import java.util.Date
import java.util.Locale

fun String.toBigDecimalOrNull(): BigDecimal? {
    return try {
        BigDecimal(this)
    } catch (e: NumberFormatException) {
        null
    }
}

fun String.toBigDecimalString(): String =
    (this.toBigDecimalOrNull() ?: BigDecimal.ZERO).toPlainString()

fun BigDecimal.convertToBothCurrencies(
    quoteAsset: String,
    rates: Map<String, BigDecimal>
): Pair<BigDecimal, BigDecimal> {
    return when (quoteAsset.lowercase()) {
        USDT, USD -> {
            val usdPrice = this // 1 USDT = 1 USD
            val sekRate = rates[USD_TO_SEK] ?: BigDecimal.ZERO
            val sekPrice = usdPrice * sekRate
            Pair(usdPrice, sekPrice)
        }

        INR -> {
            val inrToUsd = rates[INR_TO_USD] ?: BigDecimal.ZERO
            val inrToSek = rates[INR_TO_SEK] ?: BigDecimal.ZERO
            val usdPrice = this * inrToUsd
            val sekPrice = this * inrToSek
            Pair(usdPrice, sekPrice)
        }

        else -> Pair(BigDecimal.ZERO, BigDecimal.ZERO) // Unknown quoteAsset — cannot convert
    }
}

/**
 * To format USD and SEK based on locale, with precision up to 8 decimal places
 * formatting example: in English-US currencies show as ($10.50) or (SEK 130.75)
 * while for Sv-SE it shows (10.50 US$) or (130.75 kr)
 */
fun BigDecimal.formatCurrencyDynamic(isUsd: Boolean): String {
    val currency = Currency.getInstance(if (isUsd) "USD" else "SEK")
    val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())

    formatter.currency = currency
    formatter.maximumFractionDigits = this.scale().coerceAtMost(8)
    formatter.minimumFractionDigits = 2

    return formatter.format(this)
}

fun formatDate(timestamp: Long): String {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
    return formatter.format(date)
}