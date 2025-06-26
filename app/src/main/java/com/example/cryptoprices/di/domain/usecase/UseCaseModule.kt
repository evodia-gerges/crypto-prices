package com.example.cryptoprices.di.domain.usecase

import com.example.cryptoprices.domain.usecase.CryptoPricesUseCase
import com.example.cryptoprices.domain.usecase.CryptoPricesUseCaseImpl
import com.example.cryptoprices.domain.usecase.SettingsUseCase
import com.example.cryptoprices.domain.usecase.SettingsUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {
    factory<CryptoPricesUseCase> { CryptoPricesUseCaseImpl(get()) }
    factory<SettingsUseCase> { SettingsUseCaseImpl(get()) }
}