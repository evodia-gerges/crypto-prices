package com.example.cryptoprices.data.repo

import com.example.cryptoprices.data.network.api.ApiCryptoService
import com.example.cryptoprices.data.network.dto.CryptoCurrencyDTO
import com.example.cryptoprices.domain.model.CryptoCurrency
import com.example.cryptoprices.utils.convertToBothCurrencies
import com.example.cryptoprices.utils.toBigDecimalOrNull
import java.math.BigDecimal

interface CryptoPricesRepo {
    suspend fun getCryptoCurrencies(): List<CryptoCurrency>
}

class CryptoPricesRepoImpl(
    private val exchangeRateRepo: ExchangeRateRepo,
    private val cryptoApi: ApiCryptoService
) : CryptoPricesRepo {

    private var cachedValue: List<CryptoCurrency> = emptyList()

    /**
     * Returns a list of CryptoCurrency which contains aggregation of
     * the crypto currency and its exchange rates in USD and SEK
     */
    override suspend fun getCryptoCurrencies(): List<CryptoCurrency> {
        if (cachedValue.isNotEmpty())
            return cachedValue

        return try {
            val rates = exchangeRateRepo.getExchangeRates()
            // Runtime cache, can be improved to use a longer cache (i.e. 24 hours)
            cachedValue = cryptoApi.getCryptoCurrencies().mapToModel(rates = rates)
            cachedValue
        } catch (e: Exception) {
            emptyList()
        }
    }

    private fun List<CryptoCurrencyDTO>.mapToModel(rates: Map<String, BigDecimal>): List<CryptoCurrency> =
        this.map { dto ->
            val price = dto.lastPrice.toBigDecimalOrNull() ?: BigDecimal.ZERO
            val priceInUsdAndSek = price.convertToBothCurrencies(
                quoteAsset = dto.quoteAsset,
                rates = rates
            )
            return@map if (priceInUsdAndSek.first == BigDecimal.ZERO || priceInUsdAndSek.second == BigDecimal.ZERO) {
                // Unknown quoteAsset — cannot convert
                null
            } else {
                CryptoCurrency(
                    symbol = dto.symbol,
                    baseAsset = dto.baseAsset,
                    quoteAsset = dto.quoteAsset,
                    openPrice = dto.openPrice,
                    lowPrice = dto.lowPrice,
                    highPrice = dto.highPrice,
                    lastPrice = dto.lastPrice,
                    // add usdPrice, sekPrice
                    usdPrice = priceInUsdAndSek.first,
                    sekPrice = priceInUsdAndSek.second,
                    volume = dto.volume,
                    bidPrice = dto.bidPrice,
                    askPrice = dto.askPrice,
                    at = dto.at
                )
            }
        }.filterNotNull() // Remove Nulls, i.e. Unknown quoteAsset
}