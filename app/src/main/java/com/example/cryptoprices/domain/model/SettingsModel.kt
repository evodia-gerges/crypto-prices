package com.example.cryptoprices.domain.model

data class SettingsModel(
    val isUsd: Boolean = true,
    val order: CurrenciesOrder = CurrenciesOrder.DEFAULT,
)

enum class CurrenciesOrder {
    DEFAULT,
    ASCENDING,
    DESCENDING
}