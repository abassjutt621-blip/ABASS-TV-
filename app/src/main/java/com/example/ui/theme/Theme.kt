package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AbassCyan,
    onPrimary = Color.Black,
    primaryContainer = AbassCardElevated,
    onPrimaryContainer = AbassCyan,
    secondary = AbassGold,
    onSecondary = Color.Black,
    secondaryContainer = AbassCardSurface,
    onSecondaryContainer = AbassGoldLight,
    tertiary = AbassLiveRed,
    onTertiary = Color.White,
    background = AbassMidnight,
    onBackground = AbassTextPrimary,
    surface = AbassDarkBlue,
    onSurface = AbassTextPrimary,
    surfaceVariant = AbassCardSurface,
    onSurfaceVariant = AbassTextSecondary,
    outline = AbassBorder
)

private val LightColorScheme = DarkColorScheme // ABASS TV is a broadcast/video network app designed with a signature dark cinematic aesthetic

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Always enforce cohesive broadcast identity
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
