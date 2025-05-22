package com.example.readalready_mad_project.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Farb-Container
data class CustomColors(
    val background: Color,
    val card: Color,
    val accent: Color,
    val text: Color
)

// Light Theme
val LightCustomColors = CustomColors(
    background = PastelGray,
    card = PastelLilac,
    accent = AccentPurple,
    text = TextGray
)

// Dark Theme
val DarkCustomColors = CustomColors(
    background = DarkBackground,
    card = DarkCard,
    accent = AccentPurple,
    text = TextWhite
)

val LocalCustomColors = staticCompositionLocalOf { LightCustomColors }

// Zugriffshilfe
object ThemeColors {
    val custom: CustomColors
        @Composable get() = LocalCustomColors.current
}

// Globale Theme-Funktion
@Composable
fun ReadAlreadyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val customColors = if (darkTheme) DarkCustomColors else LightCustomColors

    CompositionLocalProvider(LocalCustomColors provides customColors) {
        MaterialTheme(
            colorScheme = if (darkTheme) darkColorScheme() else lightColorScheme(),
            typography = Typography,
            content = content
        )
    }
}
