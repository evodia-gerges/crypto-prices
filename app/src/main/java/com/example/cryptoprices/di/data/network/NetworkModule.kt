package com.example.cryptoprices.di.data.network

import com.example.cryptoprices.data.network.api.ApiCryptoService
import com.example.cryptoprices.data.network.api.ApiExchangeRateService
import com.google.gson.GsonBuilder
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single {
        Retrofit.Builder()
            .baseUrl(cryptoBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }

    single<ApiCryptoService> { get<Retrofit>().create(ApiCryptoService::class.java) }

    single(named("currencyExchangeRate")) {
        Retrofit.Builder()
            .baseUrl(currencyExchangeRateBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<ApiExchangeRateService> {
        get<Retrofit>(named("currencyExchangeRate")).create(
            ApiExchangeRateService::class.java
        )
    }
}

private const val cryptoBaseUrl = "https://api.wazirx.com/"
private const val currencyExchangeRateBaseUrl = "https://api.frankfurter.app/"

