package com.cmp.starter_pack.common.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

@Composable
fun AppTheme(isDark: Boolean = true, content: @Composable () -> Unit) {
    val colors = if (isDark) darkColors else lightColors

    CompositionLocalProvider(Colors provides colors) { content() }
}

val darkColors = ColorScheme(
    content = Palette(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFB0B0B0),
        accent = Color(0xFF00BCD4),
        negative = Color(0xFFFF5722),
        positive = Color(0xFF4CAF50),
        transparent = Color(0x00000000)
    ),
    background = Palette(
        primary = Color(0xFF121212),
        secondary = Color(0xFF1E1E1E),
        accent = Color(0xFF2D2D2D),
        negative = Color(0xFF3E2723),
        positive = Color(0xFF1B5E20),
        transparent = Color(0x00000000)
    ),
    button = Palette(
        primary = Color(0xFF6200EE),
        secondary = Color(0xFF3700B3),
        accent = Color(0xFF03DAC6),
        negative = Color(0xFFD32F2F),
        positive = Color(0xFF388E3C),
        transparent = Color(0x00000000)
    )
)

val lightColors = ColorScheme(
    content = Palette(
        primary = Color(0xFF000000),
        secondary = Color(0xFF757575),
        accent = Color(0xFF0097A7),
        negative = Color(0xFFD32F2F),
        positive = Color(0xFF388E3C),
        transparent = Color(0x00000000)
    ),
    background = Palette(
        primary = Color(0xFFFFFFFF),
        secondary = Color(0xFFF5F5F5),
        accent = Color(0xFFE0E0E0),
        negative = Color(0xFFFFEBEE),
        positive = Color(0xFFE8F5E8),
        transparent = Color(0x00000000)
    ),
    button = Palette(
        primary = Color(0xFF6200EE),
        secondary = Color(0xFF3700B3),
        accent = Color(0xFF03DAC6),
        negative = Color(0xFFD32F2F),
        positive = Color(0xFF388E3C),
        transparent = Color(0x00000000)
    )
)