package com.example.cryptoprices.data.repo

import com.example.cryptoprices.data.network.api.ApiExchangeRateService
import java.math.BigDecimal

interface ExchangeRateRepo {
    suspend fun getExchangeRates(): Map<String, BigDecimal>
}

class ExchangeRateRepoImpl(
    private val exchangeRateApi: ApiExchangeRateService
) : ExchangeRateRepo {
    override suspend fun getExchangeRates(): Map<String, BigDecimal> {
        return try {
            getExchangeRatesMap()
        } catch (e: Exception) {
            emptyMap()
        }
    }

    private suspend fun getExchangeRatesMap(): Map<String, BigDecimal> {
        return mapOf(
            USD_TO_SEK to (exchangeRateApi.getRates(USD, SEK).rates[SEK.uppercase()] ?: BigDecimal.ZERO),
            INR_TO_USD to (exchangeRateApi.getRates(INR, USD).rates[USD.uppercase()] ?: BigDecimal.ZERO),
            INR_TO_SEK to (exchangeRateApi.getRates(INR, SEK).rates[SEK.uppercase()] ?: BigDecimal.ZERO)
        )
    }

    companion object {
        const val USD_TO_SEK = "usd_to_sek"
        const val INR_TO_USD = "inr_to_usd"
        const val INR_TO_SEK = "inr_to_sek"
        const val USD = "usd"
        const val SEK = "sek"
        const val INR = "inr"
        const val USDT = "usdt"
    }
}