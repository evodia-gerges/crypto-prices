package com.example.cryptoprices

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.cryptoprices.ui.navigation.AppNavigation
import com.example.cryptoprices.ui.theme.CryptoPricesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoPricesTheme {
                AppNavigation()
            }
        }
    }
}