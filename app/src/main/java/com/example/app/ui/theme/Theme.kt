package com.example.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = BackgroundDark,
    surface = CardDark,
    primary = TextTitleDark,
    secondary = TextBodyDark,
    surfaceVariant = DropMenuDark,
    onSurfaceVariant = DropMenuTextDark,
    tertiary = ButtonBody,
    onTertiary = TextButton
)

private val LightColorScheme = lightColorScheme(
    background = BackgroundLight,
    surface = CardLight,
    primary = TextTitleLight,
    secondary = TextBodyLight,
    surfaceVariant = DropMenuLight,
    onSurfaceVariant = DropMenuTextLight,
    tertiary = ButtonBody,
    onTertiary = TextButton
)

@Composable
fun TaskSchedulerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}