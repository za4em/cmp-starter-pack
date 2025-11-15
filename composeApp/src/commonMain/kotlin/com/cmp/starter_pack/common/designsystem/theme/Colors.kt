package com.cmp.starter_pack.common.designsystem.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

val Colors = compositionLocalOf<ColorScheme> { error("No ColorScheme provide provided") }

data class ColorScheme(
    val content: Palette,
    val background: Palette,
    val button: Palette,
)

data class Palette(
    val primary: Color,
    val secondary: Color,
    val accent: Color,
    val negative: Color,
    val positive: Color,
    val transparent: Color,
)