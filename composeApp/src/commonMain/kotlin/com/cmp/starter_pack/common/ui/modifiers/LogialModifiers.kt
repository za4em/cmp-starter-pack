package com.cmp.starter_pack.common.ui.modifiers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Modifier.thenIf(condition: Boolean, block: @Composable Modifier.() -> Modifier): Modifier {
    return if (condition) this.block() else this
}

@Composable
fun <T> Modifier.thenIfNotNull(value: T?, block: @Composable Modifier.(T) -> Modifier): Modifier {
    return if (value != null) this.block(value) else this
}
