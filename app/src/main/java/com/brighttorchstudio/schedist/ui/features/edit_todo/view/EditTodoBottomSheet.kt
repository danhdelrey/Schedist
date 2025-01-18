package com.brighttorchstudio.schedist.ui.features.edit_todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel
import com.brighttorchstudio.schedist.ui.shared_view.ImportanceDropdownButton
import com.brighttorchstudio.schedist.ui.shared_view.StyledTextField
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedTimeText
import com.brighttorchstudio.schedist.ui.shared_view.schedule.ScheduleBottomSheet
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDateTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoBottomSheet(
    todo: Todo? = null,
    viewModel: EditTodoViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    var dateTime by remember { mutableStateOf(if (todo != null) todo.dateTime else LocalDateTime.now()) }
    var inputTodoTitle by remember { mutableStateOf(if (todo != null) todo.title else "") }
    var inputTodoDescription by remember { mutableStateOf(if (todo != null) todo.description else "") }
    var selectedImportance by remember {
        mutableStateOf(if (todo != null) todo.importanceLevel else ImportanceLevel.NORMAL)
    }
    var reminderEnabled by remember { mutableStateOf(if (todo != null) todo.reminderEnabled else true) }

    var showScheduleBottomSheet by remember { mutableStateOf(false) }
    if (showScheduleBottomSheet) {
        ScheduleBottomSheet(
            onDismiss = {
                showScheduleBottomSheet = false
            },
            initialDateTime = dateTime,
            onSubmitted = { selectedDateTime, selectedReminderEnabled ->
                dateTime = selectedDateTime
                reminderEnabled = selectedReminderEnabled
            },
            reminderEnabled = reminderEnabled
        )
    }


    val focusRequester = remember { FocusRequester() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.height(300.dp),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = {},
    ) {

        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
            if (todo == null) {
                dateTime = LocalDateTime.now()
            }
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            Column {
                FormattedTimeText(dateTime)

                StyledTextField(
                    value = inputTodoTitle,
                    onValueChange = { inputTodoTitle = it },
                    placeholderText = "Tên nhiệm vụ",
                    textStyle = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    modifier = Modifier.focusRequester(focusRequester)
                )

                StyledTextField(
                    value = inputTodoDescription,
                    onValueChange = { inputTodoDescription = it },
                    placeholderText = "Nhập mô tả cho nhiệm vụ này...",
                    textStyle = MaterialTheme.typography.bodyLarge,
                    maxLines = 3
                )


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer
                    )
            ) {
                Spacer(
                    modifier = Modifier
                        .width(10.dp)
                )
                ImportanceDropdownButton(
                    initialSelectedItem = selectedImportance,
                    onSelectedOption = { newImportance ->
                        selectedImportance = newImportance
                    }
                )
                IconButton(
                    onClick = {
                        showScheduleBottomSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date"
                    )
                }
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                IconButton(
                    onClick = {
                        onDismiss()
                        if (todo != null) {
                            viewModel.updateTodo(
                                todo = todo,
                                newTodo = Todo(
                                    id = todo.id,
                                    title = inputTodoTitle,
                                    description = inputTodoDescription,
                                    importanceLevel = selectedImportance,
                                    dateTime = dateTime,
                                    reminderEnabled = false
                                )
                            )
                            viewModel.showUpdatedTodoSnackbar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                            )
                        } else {
                            viewModel.addTodo(
                                Todo(
                                    id = UUID.randomUUID().toString(),
                                    title = inputTodoTitle,
                                    description = inputTodoDescription,
                                    importanceLevel = selectedImportance,
                                    dateTime = dateTime,
                                    reminderEnabled = false
                                )
                            )
                            viewModel.showAddedTodoSnackbar(
                                scope = scope,
                                snackbarHostState = snackbarHostState,
                            )
                        }

                    },
                    enabled = inputTodoTitle.isNotBlank(),
                    colors = IconButtonDefaults.iconButtonColors().copy(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Add",
                    )
                }
            }
        }
    }
}