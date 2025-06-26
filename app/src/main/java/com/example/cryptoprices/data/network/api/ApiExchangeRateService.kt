package com.example.cryptoprices.data.network.api

import com.example.cryptoprices.data.network.dto.ExchangeRateDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiExchangeRateService {
    @GET("latest")
    suspend fun getRates(
        @Query("from") from: String,
        @Query("to") to: String
    ): ExchangeRateDTO
}