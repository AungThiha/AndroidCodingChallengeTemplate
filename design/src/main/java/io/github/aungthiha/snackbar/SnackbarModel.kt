package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import io.github.aungthiha.models.UiText

class SnackbarModel(
    val message: UiText,
    val actionLabel: UiText?,
    val withDismissAction: Boolean,
    val duration: SnackbarDuration,
    val onActionPerform: () -> Unit,
    val onDismiss: () -> Unit,
)
