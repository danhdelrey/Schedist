package com.brighttorchstudio.schedist.ui.features.complete_todo.view

import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.complete_todo.view_model.CompleteTodoViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@Composable
fun CompleteTodoButton(
    viewModel: CompleteTodoViewModel = hiltViewModel(),
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    todo: Todo,
    onCompletedTodo: () -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(false) }

    LaunchedEffect(isChecked) {
        if (isChecked) {
            delay(100)
            viewModel.completeTodo(todo)
            viewModel.showCompletedTodoSnackbar(
                scope = scope,
                snackbarHostState = snackBarHostState
            )
            isChecked = false
            onCompletedTodo()
        }
    }

    RadioButton(
        selected = isChecked,
        onClick = {
            isChecked = true
        }
    )
}