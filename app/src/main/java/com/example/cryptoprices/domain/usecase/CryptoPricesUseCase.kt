package com.example.cryptoprices.domain.usecase

import com.example.cryptoprices.data.repo.CryptoPricesRepo
import com.example.cryptoprices.domain.model.CryptoCurrency

interface CryptoPricesUseCase {
    suspend fun getCryptoCurrencies(): List<CryptoCurrency>
}

class CryptoPricesUseCaseImpl(
    private val repo: CryptoPricesRepo
) : CryptoPricesUseCase {
    override suspend fun getCryptoCurrencies(): List<CryptoCurrency> {
        return repo.getCryptoCurrencies()
    }
}