package com.example.cryptoprices.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel
import com.google.gson.Gson
import java.net.URLDecoder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent

class DetailsViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), KoinComponent {
    private val _state = MutableStateFlow(DetailsViewState())

    val state: StateFlow<DetailsViewState> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            val currency = getCurrency()

            _state.value = _state.value.copy(
                isLoading = false,
                currency = currency
            )
        }
    }

    // Reading from SavedStateHandle,
    // an alt. way could be to have a singleton data holder (i.e. shared VM or a Repo)
    // that gets injected here in this VM (to read it), and in the Home VM (to set it)
    private fun getCurrency(): CryptoCurrencyUiModel? {
        val gson = Gson()
        val userJson = savedStateHandle.get<String>("currency")?.let {
            URLDecoder.decode(it, "UTF-8")
        }
        return gson.fromJson(userJson, CryptoCurrencyUiModel::class.java)
    }
}