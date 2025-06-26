package com.example.cryptoprices.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.domain.usecase.CryptoPricesUseCase
import com.example.cryptoprices.domain.usecase.SettingsUseCase
import com.example.cryptoprices.ui.model.CryptoCurrencyUiMapper
import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class HomeViewModel : ViewModel(), KoinComponent {
    private val useCase: CryptoPricesUseCase = get()
    private val settingsUseCase: SettingsUseCase = get()
    private val mapper = CryptoCurrencyUiMapper()
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState> = _state

    init {
        loadData()
    }

    fun toggleCurrency() = filterCurrencies(isUsd = !_state.value.isUsd)

    fun filterCurrencies(
        query: String = _state.value.query,
        isUsd: Boolean = _state.value.isUsd,
        order: CurrenciesOrder = _state.value.order
    ) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val filteredCurrencies = getFilteredCurrenciesList(query, isUsd, order)

            _state.value = _state.value.copy(
                isLoading = false,
                query = query,
                currencies = filteredCurrencies,
                isUsd = isUsd,
                order = order
            )
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            settingsUseCase.getSettings().collect { newSettings ->
                filterCurrencies(query = "", isUsd = newSettings.isUsd, order = newSettings.order)
            }
        }
    }

    private suspend fun getFilteredCurrenciesList(
        query: String,
        isUsd: Boolean,
        order: CurrenciesOrder
    ): List<CryptoCurrencyUiModel> {
        return if (query.isEmpty()) {
            getFullCurrenciesList(isUsd, order)
        } else {
            getFullCurrenciesList(isUsd, order).filter {
                it.symbol.contains(
                    query,
                    ignoreCase = true
                )
            }
        }
    }

    private suspend fun getFullCurrenciesList(
        isUsd: Boolean,
        order: CurrenciesOrder
    ): List<CryptoCurrencyUiModel> {
        val currencies = useCase.getCryptoCurrencies()

        val sortedCurrencies = when (order) {
            CurrenciesOrder.ASCENDING -> currencies.sortedBy { it.sekPrice }
            CurrenciesOrder.DESCENDING -> currencies.sortedByDescending { it.sekPrice }
            CurrenciesOrder.DEFAULT -> currencies
        }

        return sortedCurrencies
            .map { mapper.map(it, isUsd = isUsd) }
    }
}