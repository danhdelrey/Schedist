package com.brighttorchstudio.schedist.ui.features.delete_todo.view

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.delete_todo.view_model.DeleteTodoViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun DeleteTodoButton(
    viewModel: DeleteTodoViewModel = hiltViewModel(),
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    todoList: List<Todo>,
    onDeletedTodo: () -> Unit = {}
) {
    TextButton(
        onClick = {
            viewModel.deleteTodos(todoList)
            viewModel.showDeletedTodoSnackbar(
                scope = scope,
                snackBarHostState = snackBarHostState
            )
            onDeletedTodo()
        }
    ) {
        Text("Delete")
    }
}