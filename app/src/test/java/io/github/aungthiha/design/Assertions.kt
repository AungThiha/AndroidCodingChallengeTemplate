package io.github.aungthiha.design

import androidx.annotation.StringRes
import io.github.aungthiha.models.UiText
import org.junit.jupiter.api.Assertions

infix fun UiText.shouldEqualTo(expected: String) {
    Assertions.assertEquals(
        UiText(expected),
        this
    )
}

infix fun UiText.shouldEqualTo(@StringRes expected: Int) {
    Assertions.assertEquals(
        UiText(expected),
        this
    )
}
