package com.example.cryptoprices.ui.model

import com.example.cryptoprices.domain.model.CryptoCurrency
import com.example.cryptoprices.utils.formatCurrencyDynamic
import com.example.cryptoprices.utils.formatDate
import com.example.cryptoprices.utils.toBigDecimalString

data class CryptoCurrencyUiModel(
    val symbol: String,
    val baseAsset: String,
    val formattedPrice: String, // price in USD/SEK

    val formattedOpenPrice: String, // openPrice in quoteAsset currency (i.e. 303000.0 inr)
    val formattedLastPrice: String, // lastPrice in quoteAsset currency (i.e. 303000.0 inr)
    val formattedHighPrice: String, // highPrice in quoteAsset currency (i.e. 303000.0 inr)
    val formattedLowPrice: String, // lowPrice in quoteAsset currency (i.e. 303000.0 inr)
    val formattedDate: String
)

class CryptoCurrencyUiMapper {
    fun map(cryptoCurrency: CryptoCurrency, isUsd: Boolean = true): CryptoCurrencyUiModel {
        return CryptoCurrencyUiModel(
            symbol = cryptoCurrency.symbol,
            baseAsset = cryptoCurrency.baseAsset,
            formattedPrice =
                (if (isUsd) cryptoCurrency.usdPrice else cryptoCurrency.sekPrice)
                    .formatCurrencyDynamic(isUsd),
            formattedOpenPrice =
                "${cryptoCurrency.openPrice.toBigDecimalString()} ${cryptoCurrency.quoteAsset}",
            formattedLastPrice =
                "${cryptoCurrency.lastPrice.toBigDecimalString()} ${cryptoCurrency.quoteAsset}",
            formattedHighPrice =
                "${cryptoCurrency.highPrice.toBigDecimalString()} ${cryptoCurrency.quoteAsset}",
            formattedLowPrice =
                "${cryptoCurrency.lowPrice.toBigDecimalString()} ${cryptoCurrency.quoteAsset}",
            formattedDate = formatDate(cryptoCurrency.at)
        )
    }
}