package com.brighttorchstudio.schedist.ui.features.edit_todo.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.data.todo.model.Subtask
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel

@Composable
fun SubtaskItem(
    viewModel: EditTodoViewModel = hiltViewModel(),
    subtask : Subtask,
    index : Int,
){
    var subtaskName by remember { mutableStateOf(subtask.name) }
    var subtaskState by remember { mutableStateOf(subtask.complete) }

    LaunchedEffect(subtaskState) {
        viewModel.updateSubtaskState(subtaskState,index)
    }

    Row(modifier = Modifier.padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically) {
        RadioButton(
            onClick = {subtaskState = !subtaskState},
            selected = subtaskState,
            colors = RadioButtonColors(
                selectedColor = MaterialTheme.colorScheme.outline,
                unselectedColor = RadioButtonDefaults.colors().unselectedColor,
                disabledSelectedColor = RadioButtonDefaults.colors().disabledSelectedColor,
                disabledUnselectedColor = RadioButtonDefaults.colors().disabledUnselectedColor,
            )
        )
        TextField(
            value = subtaskName,
            onValueChange = {subtaskName = it},
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color=
                if (subtaskState) MaterialTheme.colorScheme.outline
                else MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration =
                if (subtaskState) TextDecoration.LineThrough
                else TextDecoration.None
            ),
            placeholder = {
                Text(
                    text = "Nhập nhiệm vụ phụ...",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline
                )
            },
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            modifier = Modifier
                .onFocusChanged {
                        focusState ->
                    if (!focusState.isFocused){
                        if (subtaskName.isEmpty()){
                            viewModel.removeSubtask(subtask)
                        }
                        else{
                            if (subtaskName!= subtask.name)
                                viewModel.updateSubtaskName(subtaskName, index)
                        }
                    }

                }
        )
    }
}