package com.example.cryptoprices.domain.usecase

import com.example.cryptoprices.data.repo.SettingsRepo
import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.domain.model.SettingsModel
import kotlinx.coroutines.flow.StateFlow

interface SettingsUseCase {
    suspend fun getSettings(): StateFlow<SettingsModel>
    suspend fun saveIsUsd(newIsUsd: Boolean)
    suspend fun saveOrder(newOrder: CurrenciesOrder)
}

class SettingsUseCaseImpl(
    private val repo: SettingsRepo
) : SettingsUseCase {
    override suspend fun getSettings(): StateFlow<SettingsModel> {
        return repo.getSettings()
    }

    override suspend fun saveIsUsd(newIsUsd: Boolean) {
        return repo.saveIsUsd(newIsUsd)
    }

    override suspend fun saveOrder(newOrder: CurrenciesOrder) {
        return repo.saveOrder(newOrder)
    }
}