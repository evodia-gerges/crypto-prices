package com.example.cryptoprices.ui.navigation

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.SavedStateViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cryptoprices.ui.details.DETAILS_VIEW_ROUTE
import com.example.cryptoprices.ui.details.DetailsView
import com.example.cryptoprices.ui.details.DetailsViewModel
import com.example.cryptoprices.ui.home.HOME_VIEW_ROUTE
import com.example.cryptoprices.ui.home.HomeView
import com.example.cryptoprices.ui.model.CryptoCurrencyUiModel
import com.example.cryptoprices.ui.settings.SETTINGS_VIEW_ROUTE
import com.example.cryptoprices.ui.settings.SettingsView
import com.google.gson.Gson
import java.net.URLEncoder

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = HOME_VIEW_ROUTE) {
        composable(HOME_VIEW_ROUTE) { HomeView(navController) }
        composable(SETTINGS_VIEW_ROUTE) { SettingsView(navController) }

        composable(
            route = "$DETAILS_VIEW_ROUTE/{currency}",
            arguments = listOf(navArgument("currency") { type = NavType.StringType })
        ) { backStackEntry ->
            val context = LocalContext.current.applicationContext as Application
            val factory = SavedStateViewModelFactory(context, backStackEntry)

            val viewModel: DetailsViewModel = viewModel(
                viewModelStoreOwner = backStackEntry,
                factory = factory
            )

            DetailsView(navController, viewModel)
        }
    }
}

fun NavController.navigateToDetails(currency: CryptoCurrencyUiModel) {
    val currencyJson = URLEncoder.encode(Gson().toJson(currency), "UTF-8")
    navigate("$DETAILS_VIEW_ROUTE/$currencyJson")
}