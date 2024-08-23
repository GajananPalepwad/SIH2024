package com.plcoding.backgroundlocationtracking.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColorScheme(
    primary = appColorForDark,
    secondary = appColorForDark,
    tertiary = appColorTransparentDark,
    background = LightGray,
    surface = DarkGray,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.Gray,
    onSurface = Color.White,
)

private val LightColorPalette = lightColorScheme(
    primary = appColorForLight,
    secondary = appColorForLight,
    tertiary = appColorTransparentLight,
    background = LightGrayLight,
    surface = DarkGrayLight,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Gray,
    onSurface = Color.Black
)

@Composable
fun BackgroundLocationTrackingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    // Initialize typography using MaterialTheme's default typography
    val typography = Typography()

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
