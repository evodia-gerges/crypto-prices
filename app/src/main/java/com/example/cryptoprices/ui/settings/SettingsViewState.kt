package com.example.cryptoprices.ui.settings

import com.example.cryptoprices.ui.model.SettingsUiModel

data class SettingsViewState(
    val isLoading: Boolean = false,
    val settings: SettingsUiModel = SettingsUiModel(),
    val error: String? = null
)