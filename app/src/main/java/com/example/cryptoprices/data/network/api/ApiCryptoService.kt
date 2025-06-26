package com.example.cryptoprices.data.network.api

import com.example.cryptoprices.data.network.dto.CryptoCurrencyDTO
import retrofit2.http.GET

interface ApiCryptoService {
    @GET("sapi/v1/tickers/24hr")
    suspend fun getCryptoCurrencies(): List<CryptoCurrencyDTO>
}