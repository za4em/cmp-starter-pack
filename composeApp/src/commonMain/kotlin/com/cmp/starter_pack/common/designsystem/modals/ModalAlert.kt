package com.cmp.starter_pack.common.designsystem.modals

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Popup
import com.cmp.starter_pack.common.designsystem.theme.Colors

@Composable
fun ModalAlert(
    close: () -> Unit,
    visible: Boolean = true,
    content: @Composable () -> Unit,
) {

    var showAnimatedDialog by remember { mutableStateOf(false) }

    LaunchedEffect(visible) { if (visible) showAnimatedDialog = true }

    if (showAnimatedDialog) {

        Popup(onDismissRequest = close) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                var animateIn by remember { mutableStateOf(false) }
                LaunchedEffect(Unit) { animateIn = true }
                AnimatedVisibility(
                    visible = animateIn && visible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .pointerInput(Unit) { detectTapGestures { close() } }
                                .background(Colors.current.background.transparent)
                                .fillMaxSize()
                    )
                }
                AnimatedVisibility(
                    visible = animateIn && visible,
                    enter =
                        fadeIn(spring(stiffness = Spring.StiffnessHigh)) +
                                scaleIn(
                                    initialScale = .8f,
                                    animationSpec =
                                        spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessMediumLow,
                                        ),
                                ),
                    exit = slideOutVertically { it / 8 } + fadeOut() + scaleOut(targetScale = .95f),
                ) {
                    content()

                    DisposableEffect(Unit) { onDispose { showAnimatedDialog = false } }
                }
            }
        }
    }
}
