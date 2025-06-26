package com.example.cryptoprices.ui.details

import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel

data class DetailsViewState(
    val isLoading: Boolean = false,
    val currency: CryptoCurrencyUiModel? = null,
    val error: String? = null
)