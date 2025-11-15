package com.cmp.starter_pack.common.ui

import android.app.Activity
import android.content.Context
import androidx.core.view.WindowCompat

fun Context.changeBarsColors(
    statusBarLight: Boolean,
    navigationBarLight: Boolean,
) {
    (this as? Activity)?.window?.run {
        val controller = WindowCompat.getInsetsController(this, decorView)
        controller.isAppearanceLightStatusBars = statusBarLight
        controller.isAppearanceLightNavigationBars = navigationBarLight
    }
}