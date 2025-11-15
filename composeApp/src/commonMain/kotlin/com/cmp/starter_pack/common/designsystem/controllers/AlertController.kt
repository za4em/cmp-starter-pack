package com.cmp.starter_pack.common.designsystem.controllers

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.AnnotatedString

val LocalAlertController = compositionLocalOf<AlertController> { error("No AlertController provided") }

data class AlertState(
    val title: String,
    val subtitle: AnnotatedString,
    val button: String,
) {

    constructor(
        title: String,
        subtitle: String,
        button: String,
    ) : this(
        title = title,
        subtitle = AnnotatedString(subtitle),
        button = button,
    )
}

class AlertController {

    val alertState = mutableStateOf<AlertState?>(null)

    fun showDialog(dialog: AlertState) {
        alertState.value = dialog
    }
}
