package com.cmp.starter_pack

import androidx.compose.ui.window.ComposeUIViewController
import com.cmp.starter_pack.data.PlatformFactory
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    val platformFactory = PlatformFactory()
    return ComposeUIViewController { App(platformFactory) }
}