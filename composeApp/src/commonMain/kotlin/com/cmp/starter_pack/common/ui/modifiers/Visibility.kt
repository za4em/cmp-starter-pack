package com.cmp.starter_pack.common.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha

fun Modifier.visible(visible: Boolean): Modifier {
    return alpha(if (visible) 1f else 0f)
}
