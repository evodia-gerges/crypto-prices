package com.example.cryptoprices.di.data.repo

import com.example.cryptoprices.data.repo.CryptoPricesRepo
import com.example.cryptoprices.data.repo.CryptoPricesRepoImpl
import com.example.cryptoprices.data.repo.ExchangeRateRepo
import com.example.cryptoprices.data.repo.ExchangeRateRepoImpl
import com.example.cryptoprices.data.repo.SettingsRepo
import com.example.cryptoprices.data.repo.SettingsRepoImpl
import org.koin.dsl.module

val repoModule = module {
    single<ExchangeRateRepo> { ExchangeRateRepoImpl(get()) }
    single<CryptoPricesRepo> { CryptoPricesRepoImpl(get(), get()) }
    single<SettingsRepo> { SettingsRepoImpl(get()) }
}