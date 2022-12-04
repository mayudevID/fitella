package com.maulana.fitella.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.maulana.fitella.R

private val DarkColorPalette = darkColors(
    primary = Color1,
    primaryVariant = Color2,
    secondary = Color4
)

private val LightColorPalette = lightColors(
    primary = Color1,
    primaryVariant = Color2,
    secondary = Color4
)

@Composable
fun FitellaTheme(activity: Activity, darkTheme: Boolean, content: @Composable () -> Unit) {
    lateinit var colors: Colors

    if (darkTheme) {
        colors = DarkColorPalette
        activity.window.statusBarColor = ContextCompat.getColor(activity.applicationContext, R.color.black)
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).isAppearanceLightStatusBars = false
    } else {
        colors = LightColorPalette
        activity.window.statusBarColor = ContextCompat.getColor(activity.applicationContext, R.color.white)
        WindowCompat.getInsetsController(activity.window, activity.window.decorView).isAppearanceLightStatusBars = true
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}