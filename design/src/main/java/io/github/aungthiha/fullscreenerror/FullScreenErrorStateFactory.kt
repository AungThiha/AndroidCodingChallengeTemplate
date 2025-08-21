package io.github.aungthiha.fullscreenerror

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.NetworkCheck
import io.github.aungthiha.design.R

fun createGeneralFullScreenErrorState(
    buttonText: String,
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_something_went_wrong,
    body = R.string.something_went_wrong,
    icon = Icons.Default.ErrorOutline,
    button = ButtonState(
        text = buttonText,
        onClick = onButtonClick
    )
)

fun createGeneralFullScreenErrorState(
    @StringRes buttonText: Int,
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_something_went_wrong,
    body = R.string.something_went_wrong,
    icon = Icons.Default.ErrorOutline,
    button = ButtonState(
        text = buttonText,
        onClick = onButtonClick
    )
)

fun createGeneralFullScreenErrorState(
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_something_went_wrong,
    body = R.string.something_went_wrong,
    icon = Icons.Default.ErrorOutline,
    button = ButtonState(
        text = R.string.retry,
        onClick = onButtonClick
    )
)

fun createNetworkFullScreenErrorState(
    buttonText: String,
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_network_error,
    body = R.string.check_your_internet_connection,
    icon = Icons.Default.NetworkCheck,
    button = ButtonState(
        text = buttonText,
        onClick = onButtonClick
    )
)

fun createNetworkFullScreenErrorState(
    @StringRes buttonText: Int,
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_network_error,
    body = R.string.check_your_internet_connection,
    icon = Icons.Default.NetworkCheck,
    button = ButtonState(
        text = buttonText,
        onClick = onButtonClick
    )
)

fun createNetworkFullScreenErrorState(
    onButtonClick: () -> Unit
) = FullScreenErrorState(
    title = R.string.title_network_error,
    body = R.string.check_your_internet_connection,
    icon = Icons.Default.NetworkCheck,
    button = ButtonState(
        text = R.string.retry,
        onClick = onButtonClick
    )
)
