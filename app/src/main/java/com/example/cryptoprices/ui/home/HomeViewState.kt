package com.example.cryptoprices.ui.home

import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel

data class HomeViewState(
    val isLoading: Boolean = false,
    val currencies: List<CryptoCurrencyUiModel> = listOf(),
    val isUsd: Boolean = true,
    val order: CurrenciesOrder = CurrenciesOrder.DEFAULT,
    val query: String = "",
    val error: String? = null
)