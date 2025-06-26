package com.example.cryptoprices.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cryptoprices.R
import com.example.cryptoprices.ui.component.AnimatedSearchableTopAppBar
import com.example.cryptoprices.ui.component.LoadingSpinner
import com.example.cryptoprices.ui.component.TextWithRoundBackground
import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel
import com.example.cryptoprices.ui.navigation.navigateToDetails
import com.example.cryptoprices.ui.settings.SETTINGS_VIEW_ROUTE

const val HOME_VIEW_ROUTE = "home_view_route"

@Composable
fun HomeView(navController: NavHostController) {
    val viewModel: HomeViewModel = viewModel()
    val viewState: HomeViewState by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AnimatedSearchableTopAppBar(
                title = stringResource(R.string.app_name),
                initialQuery = viewState.query,
                onSearchQueryChanged = { query ->
                    viewModel.filterCurrencies(query)
                },
                hasSettings = true,
                onSettingsClicked = { navController.navigate(SETTINGS_VIEW_ROUTE)}
            )
        },
        bottomBar = {
            if (viewState.currencies.isNotEmpty()){
                BottomActions(viewModel = viewModel, viewState = viewState)
            }
        }
    ) { innerPadding ->
        // Main content
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            when {
                viewState.isLoading -> LoadingSpinner()
                viewState.currencies.isEmpty() -> NoDate()
                else -> CurrenciesList(navController = navController, viewState = viewState)
            }
        }
    }
}

@Composable
private fun NoDate() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.no_results),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun CurrenciesList(
    navController: NavHostController,
    viewState: HomeViewState
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(viewState.currencies) { currency ->
            CurrencyItem(navController, currency)
        }
    }
}

@Composable
private fun CurrencyItem(navController: NavHostController, currency: CryptoCurrencyUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable { navController.navigateToDetails(currency) }
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextWithRoundBackground(
                    modifier = Modifier.padding(end = 8.dp),
                    text = currency.baseAsset,
                    background = MaterialTheme.colorScheme.surfaceContainerHigh,
                    tint = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = currency.symbol,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Text(
                text = currency.formattedPrice,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun BottomActions(
    viewModel: HomeViewModel,
    viewState: HomeViewState
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!viewState.isLoading) {
            Button(
                onClick = { viewModel.toggleCurrency() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentPadding = PaddingValues(vertical = 20.dp)
            ) {
                Text(
                    text =
                        if (viewState.isUsd) stringResource(R.string.show_sek)
                        else stringResource(R.string.show_usd),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}