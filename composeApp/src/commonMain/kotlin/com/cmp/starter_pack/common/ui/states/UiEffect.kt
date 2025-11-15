package com.cmp.starter_pack.common.ui.states

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

@Composable
fun <T : Any> ObserveEffect(
    effectChannel: Channel<T>,
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    collector: (T) -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(collector) {
        lifecycleOwner.repeatOnLifecycle(lifecycleState) {
            effectChannel.receiveAsFlow().collect(collector)
        }
    }
}
