package com.cmp.starter_pack.common.ui.utils

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.LifecycleResumeEffect

@Composable
fun OnLifecycleEvent(onResume: () -> Unit = {}, onPause: () -> Unit = {}) {
    LifecycleResumeEffect(Unit) {
        onResume()

        onPauseOrDispose { onPause() }
    }
}
