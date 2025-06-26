package com.example.cryptoprices.domain.model

import java.math.BigDecimal

data class CryptoCurrency(
    val symbol: String,
    val baseAsset: String,
    val quoteAsset: String,
    val openPrice: String,
    val lowPrice: String,
    val highPrice: String,
    val lastPrice: String,
    val usdPrice: BigDecimal,
    val sekPrice: BigDecimal,
    val volume: String,
    val bidPrice: String,
    val askPrice: String,
    val at: Long
)