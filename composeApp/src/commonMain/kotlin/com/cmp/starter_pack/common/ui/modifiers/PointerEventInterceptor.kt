package com.cmp.starter_pack.common.ui.modifiers

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEvent
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput

fun Modifier.unconsumedPointerEventsInterceptor(onEvent: (event: PointerEvent) -> Unit) =
    this.pointerInput(Unit) {
        awaitEachGesture {
            val event = awaitPointerEvent(PointerEventPass.Main)
            if (event.changes.any { change -> !change.isConsumed }) {
                onEvent(event)
            }
        }
    }
