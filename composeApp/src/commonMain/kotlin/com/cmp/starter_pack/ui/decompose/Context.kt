package com.cmp.starter_pack.ui.decompose


import com.arkivanov.decompose.childContext
import com.arkivanov.decompose.router.children.NavigationSource
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.statekeeper.SerializableContainer
import com.arkivanov.essenty.statekeeper.consumeRequired
import com.cmp.starter_pack.data.DataContainer
import kotlinx.serialization.KSerializer
import kotlin.coroutines.CoroutineContext
import com.arkivanov.decompose.ComponentContext as DecomposeComponentContext

interface ComponentContext : DecomposeComponentContext {
    val container: DataContainer
    val dispatcher: CoroutineContext
}

class DefaultComponent(
    componentContext: DecomposeComponentContext,
    override val container: DataContainer,
    override val dispatcher: CoroutineContext,
) : ComponentContext, DecomposeComponentContext by componentContext

fun ComponentContext.childComponent(key: String, lifecycle: Lifecycle? = null): ComponentContext =
    DefaultComponent(
        componentContext = childContext(key = key, lifecycle = lifecycle),
        container = container,
        dispatcher = dispatcher,
    )

fun <C : Any, T : Any> ComponentContext.childStack(
    source: NavigationSource<StackNavigation.Event<C>>,
    serializer: KSerializer<C>?,
    initialConfiguration: C,
    key: String = "DefaultChildStack",
    handleBackButton: Boolean = false,
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildStack<C, T>> =
    childStack(
        source = source,
        serializer = serializer,
        initialStack = { listOf(initialConfiguration) },
        key = key,
        handleBackButton = handleBackButton,
        childFactory = { configuration: C, componentContext ->
            childFactory(
                configuration, DefaultComponent(
                    componentContext = componentContext,
                    container = container,
                    dispatcher = dispatcher,
                )
            )
        },
    )

fun <C : Any, T : Any> ComponentContext.childStack(
    source: NavigationSource<StackNavigation.Event<C>>,
    serializer: KSerializer<C>?,
    initialConfiguration: List<C>,
    key: String = "DefaultChildStack",
    handleBackButton: Boolean = false,
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildStack<C, T>> =
    childStack(
        source = source,
        serializer = serializer,
        initialStack = { initialConfiguration },
        key = key,
        handleBackButton = handleBackButton,
        childFactory = { configuration: C, componentContext ->
            childFactory(
                configuration, DefaultComponent(
                    componentContext = componentContext,
                    container = container,
                    dispatcher = dispatcher,
                )
            )
        },
    )

fun <C : Any, T : Any> ComponentContext.childSlot(
    source: NavigationSource<SlotNavigation.Event<C>>,
    serializer: KSerializer<C>?,
    initialConfiguration: () -> C? = { null },
    key: String = "DefaultChildSlot",
    handleBackButton: Boolean = false,
    childFactory: (configuration: C, ComponentContext) -> T,
): Value<ChildSlot<C, T>> =
    childSlot(
        source = source,
        saveConfiguration = { configuration ->
            if ((serializer != null) && (configuration != null)) {
                SerializableContainer(value = configuration, strategy = serializer)
            } else {
                null
            }
        },
        restoreConfiguration = { container ->
            if (serializer != null) {
                container.consumeRequired(strategy = serializer)
            } else {
                null
            }
        },
        key = key,
        initialConfiguration = initialConfiguration,
        handleBackButton = handleBackButton,
        childFactory = { configuration: C, componentContext ->
            childFactory(
                configuration, DefaultComponent(
                    componentContext = componentContext,
                    container = container,
                    dispatcher = dispatcher,
                )
            )
        },
    )
