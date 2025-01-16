package com.brighttorchstudio.schedist.ui.features.update_todo.view

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.ui.features.update_todo.view_model.UpdateTodoViewModel

@Composable
fun FABAddTodo(
    viewModel: UpdateTodoViewModel = hiltViewModel(),
) {
    FloatingActionButton(
        onClick = {
            viewModel.addTodo()
        },
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add")
    }
}