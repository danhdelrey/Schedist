package com.brighttorchstudio.schedist.ui.features.manage_todo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.manage_todo.view_model.ManageTodoViewModel

@Composable
fun TodoList(
    todoList: List<Todo>,
    innerPadding: PaddingValues,
    viewModel: ManageTodoViewModel = hiltViewModel(),
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .verticalScroll(rememberScrollState())
    ) {
        todoList.forEach { todo ->
            TodoItem(
                todo = todo,
                viewModel = viewModel,
                isSelected = todo in selectedTodos,
                onToggleSelection = { viewModel.toggleTodoSelection(todo) },
                onClick = {}
            )
        }
    }
}