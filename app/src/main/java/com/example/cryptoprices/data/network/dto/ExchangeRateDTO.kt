package com.example.cryptoprices.data.network.dto

import java.math.BigDecimal

data class ExchangeRateDTO(
    val amount: BigDecimal,
    val base: String,
    val date: String,
    val rates: Map<String, BigDecimal> // example: "rates":{"SEK":9.432}
)