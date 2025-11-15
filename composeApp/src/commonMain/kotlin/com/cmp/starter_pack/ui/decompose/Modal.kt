package com.cmp.starter_pack.ui.decompose

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.cmp.starter_pack.common.designsystem.modals.ModalAlert
import kotlinx.serialization.KSerializer

class Modal<Config : Any, Comp : Any>(
    context: ComponentContext,
    serializer: KSerializer<Config>?,
    key: String,
    handleBackButton: Boolean = true,
    createComponent: Modal<Config, Comp>.(Config, ComponentContext) -> Comp,
) {
    val navigation = SlotNavigation<Config>()

    val modal: Value<ChildSlot<Config, Comp>> =
        context.childSlot(
            source = navigation,
            serializer = serializer,
            handleBackButton = handleBackButton,
            key = key,
        ) { config, childComponentContext ->
            createComponent(
                config,
                childComponentContext,
            )
        }

    fun show(key: Config) {
        navigation.activate(key)
    }

    fun close() {
        navigation.dismiss()
    }
}

@Composable
fun <Config : Any, Comp : Any> ModalAlert(
    modal: Modal<Config, Comp>,
    alert: @Composable (Comp) -> Unit,
) {
    val state = modal.modal.subscribeAsState()
    val modalComponent = state.value.child?.instance

    ModalAlert(
        visible = modalComponent != null,
        close = modal::close,
    ) {
        modalComponent?.let {
            alert(modalComponent)
        }
    }
}