package com.example.cryptoprices.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.domain.usecase.SettingsUseCase
import com.example.cryptoprices.ui.model.mapToUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class SettingsViewModel : ViewModel(), KoinComponent {
    private val useCase: SettingsUseCase = get()
    private val _state = MutableStateFlow(SettingsViewState())

    val state: StateFlow<SettingsViewState> = _state

    init {
        loadData()
    }

    fun saveIsUsd(newIsUsd: Boolean){
        viewModelScope.launch {
            useCase.saveIsUsd(newIsUsd)
            loadData()
        }
    }

    fun saveOrder(newOrder: CurrenciesOrder){
        viewModelScope.launch {
            useCase.saveOrder(newOrder)
            loadData()
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val settings = useCase.getSettings().value.mapToUiModel()

            _state.value = _state.value.copy(
                isLoading = false,
                settings = settings
            )
        }
    }
}