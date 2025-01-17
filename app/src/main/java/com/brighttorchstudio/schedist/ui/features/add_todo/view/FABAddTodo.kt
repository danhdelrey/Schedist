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
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.ui.features.add_todo.view_model.AddTodoViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun FABAddTodo(
    viewModel: AddTodoViewModel = hiltViewModel(),
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    AddTodoBottomSheet(
        showBottomSheet = showBottomSheet,
        onDismiss = {
            showBottomSheet = false
            viewModel.addTodo()
            viewModel.showAddedTodoSnackbar(scope, snackbarHostState)
        }
    )

    FloatingActionButton(
        onClick = {
            showBottomSheet = true

        },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}