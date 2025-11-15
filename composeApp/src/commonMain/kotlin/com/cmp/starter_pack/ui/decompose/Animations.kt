package com.cmp.starter_pack.ui.decompose

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.FaultyDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.animation.*
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler

@OptIn(ExperimentalDecomposeApi::class)
fun <C : Any, T : Any> predictive(
    backHandler: BackHandler,
    back: () -> Unit,
): StackAnimation<C, T> =
    predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation = stackAnimation(slide() + fade()),
        onBack = back,
    )

@OptIn(ExperimentalDecomposeApi::class, FaultyDecomposeApi::class)
fun <C : Any, T : Any> predictive(
    backHandler: BackHandler,
    back: () -> Unit,
    onlyFade: (C, C) -> Boolean = { _, _ -> true },
): StackAnimation<C, T> =
    predictiveBackAnimation(
        backHandler = backHandler,
        fallbackAnimation =
            stackAnimation { child, otherChild, _ ->
                if (onlyFade(child.configuration, otherChild.configuration)) {
                    fade()
                } else {
                    slide() + fade()
                }
            },
        onBack = back,
    )

fun <C : Any, T : Any> rootAnimation(): StackAnimation<C, T> =
    stackAnimation(fade())

fun <Config : Any, Child : Any> Value<ChildStack<Config, Child>>.popOrBack(
    pop: () -> Unit,
    back: () -> Unit,
) {
    if (this.value.items.size > 1) pop() else back()
}