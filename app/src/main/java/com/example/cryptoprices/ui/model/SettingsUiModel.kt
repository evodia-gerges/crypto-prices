package com.example.cryptoprices.ui.model

import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.domain.model.SettingsModel

data class SettingsUiModel(
    val isUsd: Boolean = true,
    val order: CurrenciesOrder = CurrenciesOrder.DEFAULT,
)

fun SettingsModel.mapToUiModel(): SettingsUiModel {
    return SettingsUiModel(
        isUsd = this.isUsd,
        order = this.order
    )
}