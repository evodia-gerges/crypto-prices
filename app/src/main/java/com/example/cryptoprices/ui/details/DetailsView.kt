package com.example.cryptoprices.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cryptoprices.R
import com.example.cryptoprices.ui.component.TextWithRoundBackground

const val DETAILS_VIEW_ROUTE = "details_view_route"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsView(navController: NavHostController, viewModel: DetailsViewModel) {
    val viewState: DetailsViewState by viewModel.state.collectAsState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = viewState.currency?.symbol ?: stringResource(R.string.details),
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
            viewState = viewState
        )
    }
}

@Composable
private fun SettingsContent(
    modifier: Modifier,
    viewState: DetailsViewState
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.surface),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 12.dp),
            text = stringResource(R.string.details),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        viewState.currency?.let {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = MaterialTheme.shapes.large)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                HeaderRow(it.baseAsset, it.formattedDate)

                DataRow(stringResource(R.string.price), it.formattedPrice)
                DataRow(stringResource(R.string.open_price), it.formattedOpenPrice)
                DataRow(stringResource(R.string.last_price), it.formattedLastPrice)
                DataRow(stringResource(R.string.high_price), it.formattedHighPrice)
                DataRow(stringResource(R.string.low_price), it.formattedLowPrice, isLastItem = true)
            }
        }
    }
}

@Composable
private fun HeaderRow(baseAsset: String, formattedDate: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 24.dp, top = 12.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithRoundBackground(
            text = baseAsset,
            background = MaterialTheme.colorScheme.surfaceContainerHigh,
            tint = MaterialTheme.colorScheme.onSurface
        )

        Text(
            text = formattedDate,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun DataRow(key: String, value: String, isLastItem: Boolean = false) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLastItem) 16.dp else 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = key,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSurface
            )
        }

        if (!isLastItem) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.surface,
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}