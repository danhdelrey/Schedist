package com.brighttorchstudio.schedist.ui.features.add_todo.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.brighttorchstudio.schedist.ui.features.update.view.UpdateBottomSheet
import kotlinx.coroutines.CoroutineScope

@Composable
fun FABAddTodo(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    var showUpdateTodoBottomSheet by remember { mutableStateOf(false) }
    if (showUpdateTodoBottomSheet) {
        UpdateBottomSheet(
            onDismiss = {
                showUpdateTodoBottomSheet = false
            },
            scope = scope,
            snackbarHostState = snackbarHostState
        )
    }


    FloatingActionButton(
        onClick = {
            showUpdateTodoBottomSheet = true
        },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}