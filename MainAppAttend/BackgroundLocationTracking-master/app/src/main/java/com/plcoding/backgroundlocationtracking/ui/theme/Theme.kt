package com.plcoding.backgroundlocationtracking.ui.theme

import android.app.Activity
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.Typography
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.plcoding.backgroundlocationtracking.R

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

    val view = LocalView.current
    SideEffect {
        val window = (view.context as Activity).window
        val statusBarColor = if (darkTheme) {
            view.context.getColor(R.color.light_gray)
        } else {
            view.context.getColor(R.color.light_gray_light)
        }
        window.statusBarColor = statusBarColor
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        val insetsController = WindowCompat.getInsetsController(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = !darkTheme
    }

    // Initialize typography using MaterialTheme's default typography
    val typography = Typography()

    MaterialTheme(
        colorScheme = colors,
        typography = typography,
        content = content
    )
}
