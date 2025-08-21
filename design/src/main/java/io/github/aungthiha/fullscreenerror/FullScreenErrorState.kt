package io.github.aungthiha.fullscreenerror

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.aungthiha.fullscreenerror.FullScreenErrorState.ButtonState
import io.github.aungthiha.models.UiText

data class FullScreenErrorState(
    val title: UiText,
    val body: UiText,
    val icon: ImageVector? = null,
    val button: ButtonState? = null

) {
    data class ButtonState(
        val text: UiText,
        val onClick: () -> Unit
    )
}

fun ButtonState(
    text: String,
    onClick: () -> Unit
) = ButtonState(
    text = UiText.Literal(text),
    onClick = onClick
)

fun ButtonState(
    @StringRes text: Int,
    onClick: () -> Unit
) = ButtonState(
    text = UiText.Resource(text),
    onClick = onClick
)

fun FullScreenErrorState(
    @StringRes title: Int,
    @StringRes body: Int,
    icon: ImageVector? = null,
    button: ButtonState? = null
) = FullScreenErrorState(
    title = UiText.Resource(title),
    body = UiText.Resource(body),
    icon = icon,
    button = button
)

fun FullScreenErrorState(
    title: String,
    body: String,
    icon: ImageVector? = null,
    button: ButtonState? = null
) = FullScreenErrorState(
    title = UiText.Literal(title),
    body = UiText.Literal(body),
    icon = icon,
    button = button
)

fun FullScreenErrorState(
    title: String,
    @StringRes body: Int,
    icon: ImageVector? = null,
    button: ButtonState? = null
) = FullScreenErrorState(
    title = UiText.Literal(title),
    body = UiText.Resource(body),
    icon = icon,
    button = button
)

fun FullScreenErrorState(
    @StringRes title: Int,
    body: String,
    icon: ImageVector? = null,
    button: ButtonState? = null
) = FullScreenErrorState(
    title = UiText.Resource(title),
    body = UiText.Literal(body),
    icon = icon,
    button = button
)
