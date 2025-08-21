package io.github.aungthiha.snackbar

import androidx.compose.material3.SnackbarDuration
import io.github.aungthiha.models.UiText
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SnackbarStateController(
    private val snackbarMessages: MutableStateFlow<List<SnackbarModel>> = MutableStateFlow(emptyList()),
) : SnackbarEmitter, SnackbarStateFlow {

    override val snackbarStateFlow: SnackbarStateFlow = this

    override fun showSnackBar(
        message: Int,
        actionLabel: Int?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ) = snackbarMessages.update { snackbarModels ->
        snackbarModels + SnackbarModel(
            message = UiText.Resource(message),
            actionLabel = actionLabel?.let(UiText::Resource),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    }

    override fun showSnackBar(
        message: Int,
        actionLabel: String,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    ) = snackbarMessages.update { snackbarModels ->
        snackbarModels + SnackbarModel(
            message = UiText.Resource(message),
            actionLabel = UiText.Literal(actionLabel),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    }

    override fun showSnackBar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    )= snackbarMessages.update { snackbarModels ->
        snackbarModels + SnackbarModel(
            message = UiText.Literal(message),
            actionLabel = actionLabel?.let(UiText::Literal),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    }

    override fun showSnackBar(
        message: String,
        actionLabel: Int,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
        onActionPerform: () -> Unit,
        onDismiss: () -> Unit
    )= snackbarMessages.update { snackbarModels ->
        snackbarModels + SnackbarModel(
            message = UiText.Literal(message),
            actionLabel = UiText.Resource(actionLabel),
            withDismissAction = withDismissAction,
            duration = duration,
            onActionPerform = onActionPerform,
            onDismiss = onDismiss,
        )
    }

    override fun snackbarShown(snackbarModel: SnackbarModel) {
        snackbarMessages.update { snackbarModels ->
            snackbarModels.filter { it != snackbarModel }
        }
    }

    override suspend fun collect(collector: FlowCollector<List<SnackbarModel>>) {
        snackbarMessages.collect(collector)
    }
}
