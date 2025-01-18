package com.brighttorchstudio.schedist.ui.features.edit_todo.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.brighttorchstudio.schedist.R
import kotlinx.coroutines.CoroutineScope

@Composable
fun FABAddTodo(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    var showUpdateTodoBottomSheet by remember { mutableStateOf(false) }
    if (showUpdateTodoBottomSheet) {
        EditTodoBottomSheet(
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
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null
        )
    }
}