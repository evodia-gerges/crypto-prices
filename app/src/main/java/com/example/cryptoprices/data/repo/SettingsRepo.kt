package com.example.cryptoprices.data.repo

import android.content.SharedPreferences
import com.example.cryptoprices.domain.model.SettingsModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.core.content.edit
import com.example.cryptoprices.domain.model.CurrenciesOrder

interface SettingsRepo {
    suspend fun getSettings(): StateFlow<SettingsModel>
    suspend fun saveIsUsd(newIsUsd: Boolean)
    suspend fun saveOrder(newOrder: CurrenciesOrder)
}

class SettingsRepoImpl(
    private val sharedPreferences: SharedPreferences,
) : SettingsRepo {
    private val cachedValue = MutableStateFlow(SettingsModel())

    override suspend fun getSettings(): StateFlow<SettingsModel> {
        val isUsd = sharedPreferences.getBoolean("isUsd", true)
        val order =
            sharedPreferences.getString("order", CurrenciesOrder.DEFAULT.name)
                ?.let { CurrenciesOrder.valueOf(it) } ?: CurrenciesOrder.DEFAULT

        cachedValue.value = SettingsModel(isUsd = isUsd, order = order)
        return cachedValue.asStateFlow()
    }

    override suspend fun saveIsUsd(newIsUsd: Boolean) {
        sharedPreferences.edit { putBoolean("isUsd", newIsUsd) }
        cachedValue.value = SettingsModel(isUsd = newIsUsd)
    }

    override suspend fun saveOrder(newOrder: CurrenciesOrder) {
        sharedPreferences.edit { putString("order", newOrder.name) }
        cachedValue.value = SettingsModel(order = newOrder)
    }
}