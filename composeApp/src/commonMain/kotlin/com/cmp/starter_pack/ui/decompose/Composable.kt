package com.cmp.starter_pack.ui.decompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import app.cash.molecule.RecompositionMode
import app.cash.molecule.launchMolecule
import com.cmp.starter_pack.common.utils.logError
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow

abstract class ComposableComponent<State>(
    context: ComponentContext,
    recompositionMode: RecompositionMode = RecompositionMode.ContextClock,
) : ComponentContext by context {

    val states: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) {
        componentScope(dispatcher).launchMolecule(mode = recompositionMode) {
            states()
        }
    }

    @Composable
    protected abstract fun states(): State
}

abstract class ComposableActionComponent<State, Action>(
    context: ComponentContext,
    recompositionMode: RecompositionMode = RecompositionMode.ContextClock,
) : ComponentContext by context {

    private val events = Channel<Action>(capacity = Channel.BUFFERED)

    val states: StateFlow<State> by lazy(LazyThreadSafetyMode.NONE) {
        componentScope(dispatcher).launchMolecule(mode = recompositionMode) {
            states(events)
        }
    }

    fun take(action: Action) {
        events.trySend(action).onFailure {
            it?.run { logError(this) }
        }
    }

    @Composable
    protected abstract fun states(actions: Channel<Action>): State
}

@Composable
fun <T : Any> ObserveActions(
    effectChannel: Channel<T>,
    collector: suspend (T) -> Unit,
) {

    LaunchedEffect(collector) {
        effectChannel.receiveAsFlow().collect(collector)
    }
}
