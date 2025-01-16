package com.brighttorchstudio.schedist.helpers

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class UIComponentHelper {
    companion object {
        fun showSnackBar(
            scope: CoroutineScope,
            snackbarHostState: SnackbarHostState,
            message: String,
            actionLabel: String,
            onActionPerformed: () -> Unit,
        ) {
            scope.launch {
                snackbarHostState.currentSnackbarData?.dismiss()
                val result = snackbarHostState
                    .showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        onActionPerformed()
                    }

                    SnackbarResult.Dismissed -> {
                        /* Handle snackbar dismissed */
                    }
                }
            }
        }
    }
}