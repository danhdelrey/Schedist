package com.brighttorchstudio.schedist.ui.features.manage_todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel

@Composable
fun SubtaskList(
    viewModel: EditTodoViewModel = hiltViewModel(),
    todo: Todo,
    onSubtaskStateChange : () -> Unit
){
    var subtasks = todo.subtasks
    Box(modifier = Modifier
        .background(
            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
            shape = MaterialTheme.shapes.small
        )
        .padding(10.dp)){
        Column() {
            subtasks.forEach{ subtask ->
                key(subtask){
                    var subtaskState by remember { mutableStateOf(subtask.complete) }

                    LaunchedEffect(subtaskState) {
                        viewModel.setSubtaskList(subtasks)
                        viewModel.updateTodo(todo, Todo(
                            id = todo.id,
                            title = todo.title,
                            description = todo.description,
                            importanceLevel = todo.importanceLevel,
                            dateTime = todo.dateTime,
                            reminderEnabled = todo.reminderEnabled,
                            subtasks = emptyList(),
                        ))
                        onSubtaskStateChange()
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically){
                        RadioButton(
                            onClick = {
                                subtaskState = !subtaskState
                                subtask.complete=subtaskState},
                            selected = subtaskState,
                            colors = RadioButtonColors(
                                selectedColor = MaterialTheme.colorScheme.outline,
                                unselectedColor = RadioButtonDefaults.colors().unselectedColor,
                                disabledSelectedColor = RadioButtonDefaults.colors().disabledSelectedColor,
                                disabledUnselectedColor = RadioButtonDefaults.colors().disabledUnselectedColor,
                            )
                        )

                        Text(
                            text = subtask.name,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color =
                                if (subtaskState) MaterialTheme.colorScheme.outline
                                else MaterialTheme.colorScheme.onSurfaceVariant,

                                textDecoration =
                                if (subtaskState) TextDecoration.LineThrough
                                else TextDecoration.None
                            )
                        )
                    }
                }

            }
        }
    }


}