package com.cmp.starter_pack.common.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.unit.sp

object TextType {

    val lineHeightStyle =
        LineHeightStyle(
            alignment = LineHeightStyle.Alignment.Center,
            trim = LineHeightStyle.Trim.None,
        )

    val defaultStyle = TextStyle(
        fontWeight = FontWeight.Normal,
        lineHeightStyle = lineHeightStyle,
    )


    val title = defaultStyle.copy(fontSize = 28.sp, lineHeight = 34.sp)

    val body = defaultStyle.copy(fontSize = 17.sp, lineHeight = 22.sp)

    val footnote = defaultStyle.copy(fontSize = 13.sp, lineHeight = 18.sp)
}

val TextStyle.regular
    get() = copy(fontWeight = FontWeight.Normal)

val TextStyle.medium
    get() = copy(fontWeight = FontWeight.Medium)

val TextStyle.semiBold
    get() = copy(fontWeight = FontWeight.SemiBold)

val TextStyle.bold
    get() = copy(fontWeight = FontWeight.Bold)

@Composable
fun TextStyle.primary() = copy(color = Colors.current.content.primary)

@Composable
fun TextStyle.secondary() = copy(color = Colors.current.content.secondary)

@Composable
fun TextStyle.accent() = copy(color = Colors.current.content.accent)

@Composable
fun TextStyle.positive() = copy(color = Colors.current.content.positive)

@Composable
fun TextStyle.negative() = copy(color = Colors.current.content.negative)
