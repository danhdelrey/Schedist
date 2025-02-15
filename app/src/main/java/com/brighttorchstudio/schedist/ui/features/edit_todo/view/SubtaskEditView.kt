package com.brighttorchstudio.schedist.ui.features.edit_todo.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.data.todo.model.Subtask
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel

@Composable
fun SubtaskEditView(
    viewModel: EditTodoViewModel = hiltViewModel(),
){
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    val subtasks by viewModel.subtaskList.collectAsStateWithLifecycle()


    Column(modifier = Modifier) {
        subtasks.forEachIndexed{ index, subtask ->
            key(subtask) {
                SubtaskItem(subtask = subtask, index = index)
            }
        }
        var newSubtaskName by remember { mutableStateOf("") }
        var newSubtaskState by remember { mutableStateOf(false) }
        var showAddNewSubtaskTextField by remember { mutableStateOf(false) }

        if (showAddNewSubtaskTextField){

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }

            Row(modifier = Modifier.padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,) {
                RadioButton(
                    onClick = {newSubtaskState = !newSubtaskState},
                    selected = newSubtaskState,
                    colors = RadioButtonColors(
                        selectedColor = MaterialTheme.colorScheme.outline,
                        unselectedColor = RadioButtonDefaults.colors().unselectedColor,
                        disabledSelectedColor = RadioButtonDefaults.colors().disabledSelectedColor,
                        disabledUnselectedColor = RadioButtonDefaults.colors().disabledUnselectedColor,
                    )
                )
                TextField(
                    value = newSubtaskName,
                    onValueChange = {newSubtaskName = it},
                    textStyle = MaterialTheme.typography.bodyLarge.copy(
                        color=
                        if (newSubtaskState) MaterialTheme.colorScheme.outline
                        else MaterialTheme.colorScheme.onSurfaceVariant,
                        textDecoration =
                        if (newSubtaskState) TextDecoration.LineThrough
                        else TextDecoration.None),

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
                        .focusRequester(focusRequester)
                        .onFocusChanged {
                                focusState ->
                            if (!focusState.isFocused){
                                if (newSubtaskName.isNotEmpty()){
                                    viewModel.addNewSubtask(Subtask(name = newSubtaskName, complete = newSubtaskState))
                                    newSubtaskState=false
                                    newSubtaskName=""
                                }
                                showAddNewSubtaskTextField = false
                            }
                            else{
                                showAddNewSubtaskTextField = true
                            }

                        }
                )
            }
        }

        TextButton(
            onClick = {
                if (newSubtaskName.isNotEmpty()){
                    viewModel.addNewSubtask(Subtask(name = newSubtaskName, complete = newSubtaskState))
                    newSubtaskState=false
                    newSubtaskName=""
                }
                showAddNewSubtaskTextField = true
                      },

            modifier = Modifier.padding(vertical = 5.dp)
        ){
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = null,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            Text(
                text = "Thêm nhiệm vụ phụ",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }


}