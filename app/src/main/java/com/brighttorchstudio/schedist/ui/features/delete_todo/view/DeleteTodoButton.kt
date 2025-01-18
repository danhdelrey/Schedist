package com.brighttorchstudio.schedist.ui.features.delete_todo.view

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.R
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
    IconButton(
        onClick = {
            viewModel.deleteTodos(todoList)
            viewModel.showDeletedTodoSnackbar(
                scope = scope,
                snackBarHostState = snackBarHostState
            )
            onDeletedTodo()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.trash_alt),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
    }
}