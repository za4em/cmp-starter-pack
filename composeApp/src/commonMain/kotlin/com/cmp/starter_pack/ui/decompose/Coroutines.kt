package com.cmp.starter_pack.ui.decompose

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnPause
import com.arkivanov.essenty.lifecycle.doOnResume
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.coroutines.CoroutineContext

fun ComponentContext.componentScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
): CoroutineScope {
    val scope = CoroutineScope(context + SupervisorJob())
    lifecycle.doOnDestroy(scope::cancel)
    return scope
}

fun ComponentContext.lifecycleScope(
    context: CoroutineContext = Dispatchers.Main.immediate,
): CoroutineScope {
    val scope = CoroutineScope(context + SupervisorJob())
    lifecycle.doOnPause { scope.cancel() }
    return scope
}

fun ComponentContext.launchWithLifecycle(block: suspend CoroutineScope.() -> Unit) {
    doOnResume { lifecycleScope().launch(block = block) }
}

fun ComponentContext.launch(
    loader: MutableStateFlow<Boolean>,
    block: suspend CoroutineScope.() -> Unit,
) {
    if (loader.value) return

    componentScope().launch {
        loader.value = true
        block()
        loader.value = false
    }
}

fun ComponentContext.launch(block: suspend CoroutineScope.() -> Unit) {
    componentScope().launch(block = block)
}

