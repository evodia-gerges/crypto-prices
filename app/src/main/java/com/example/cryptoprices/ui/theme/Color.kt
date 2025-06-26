package com.example.cryptoprices.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val darkColors =
    darkColorScheme(
        primary = Color(0xFFFFFFFF),
        onPrimary = Color(0xFF28333E),
        tertiary = Color(0xFFFF8049),
        onTertiary = Color(0xFFFFFFFF),

        surface = Color(0xFF141B21),
        surfaceContainer = Color(0xFF1B232B),
        surfaceContainerHigh = Color(0xFF26303B),

        onSurface = Color(0xFFFFFFFF),
        onSurfaceVariant = Color(0xFFA7AEB7)
    )

val lightColors =
    lightColorScheme(
        primary = Color(0xFF28333E),
        onPrimary = Color(0xFFFFFFFF),
        tertiary = Color(0xFFE26600),
        onTertiary = Color(0xFFFFFFFF),

        surface = Color(0xFFFBFBFB),
        surfaceContainer = Color(0xFFEFF1F3),
        surfaceContainerHigh = Color(0xFFE3E8EC),

        onSurface = Color(0xFF28333E),
        onSurfaceVariant = Color(0xFF6C7580)
    )
