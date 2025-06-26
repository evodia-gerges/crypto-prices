package com.example.cryptoprices.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cryptoprices.R
import com.example.cryptoprices.domain.model.CurrenciesOrder
import com.example.cryptoprices.ui.component.SettingsCard

const val SETTINGS_VIEW_ROUTE = "settings_view_route"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsView(navController: NavHostController) {
    val viewModel: SettingsViewModel = viewModel()
    val viewState: SettingsViewState by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ChevronLeft,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        SettingsContent(
            modifier = Modifier.padding(innerPadding),
            viewModel = viewModel,
            viewState = viewState
        )
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    viewModel: SettingsViewModel,
    viewState: SettingsViewState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrencySettings(viewModel, viewState)
        SortingSettings(viewModel, viewState)
    }
}

@Composable
private fun CurrencySettings(
    viewModel: SettingsViewModel,
    viewState: SettingsViewState
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp),
        text = stringResource(R.string.default_currency),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface
    )

    SettingsCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        settings = listOf(
            SettingsCard.Select(
                stringResource(R.string.usd),
                viewState.settings.isUsd
            ) { if (!viewState.isLoading) viewModel.saveIsUsd(true) },
            SettingsCard.Select(
                stringResource(R.string.sek),
                !viewState.settings.isUsd
            ) { if (!viewState.isLoading) viewModel.saveIsUsd(false) }
        ))
}

@Composable
private fun SortingSettings(
    viewModel: SettingsViewModel,
    viewState: SettingsViewState
) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp, top = 24.dp),
        text = stringResource(R.string.list_sorting),
        style = MaterialTheme.typography.labelLarge,
        color = MaterialTheme.colorScheme.onSurface
    )

    SettingsCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        settings = listOf(
            SettingsCard.Check(
                stringResource(R.string.sorted_list),
                viewState.settings.order != CurrenciesOrder.DEFAULT
            ) {
                if (!viewState.isLoading)
                    viewModel.saveOrder(if (it) CurrenciesOrder.ASCENDING else CurrenciesOrder.DEFAULT)
            }
        ))

    if (viewState.settings.order != CurrenciesOrder.DEFAULT) {
        SettingsCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            settings = listOf(
                SettingsCard.Select(
                    stringResource(R.string.ascending),
                    viewState.settings.order == CurrenciesOrder.ASCENDING
                ) { if (!viewState.isLoading) viewModel.saveOrder(CurrenciesOrder.ASCENDING) },
                SettingsCard.Select(
                    stringResource(R.string.descending),
                    viewState.settings.order == CurrenciesOrder.DESCENDING
                ) { if (!viewState.isLoading) viewModel.saveOrder(CurrenciesOrder.DESCENDING) }
            ))
    }
}