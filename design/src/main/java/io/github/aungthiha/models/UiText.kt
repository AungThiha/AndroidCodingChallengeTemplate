package io.github.aungthiha.models

import android.content.Context
import androidx.annotation.StringRes

sealed interface UiText {
    @JvmInline
    value class Literal(val value: String) : UiText

    @JvmInline
    value class Resource(@StringRes val value: Int) : UiText
}

/**
 * Creates a [UiText.Literal] from a raw [String].
 *
 * Allows developers to instantiate either a [UiText.Literal] or [UiText.Resource] \
 * using the same `UiText(...)` function name, \
 * improving discoverability and reducing cognitive overhead.
 */
fun UiText(value: String) = UiText.Literal(value)

/**
 * Creates a [UiText.Resource] from a string resource ID.
 *
 * Allows developers to instantiate either a [UiText.Literal] or [UiText.Resource] \
 * using the same `UiText(...)` function name, \
 * improving discoverability and reducing cognitive overhead.
 */
fun UiText(@StringRes value: Int) = UiText.Resource(value)

fun UiText.unpackString(context: Context): String = when (this) {
    is UiText.Literal -> value
    is UiText.Resource -> context.getString(value)
}
