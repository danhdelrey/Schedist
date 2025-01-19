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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel
import com.brighttorchstudio.schedist.ui.shared_view.ImportanceDropdownButton
import com.brighttorchstudio.schedist.ui.shared_view.StyledTextField
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedTimeText
import com.brighttorchstudio.schedist.ui.shared_view.schedule.ScheduleBottomSheet
import java.time.LocalDateTime
import java.util.UUID

//Hiển thị một bottomsheet để thêm hoặc sửa một todo
//phải remember một boolean ở bên ngoài để ẩn hoặc hiện bottomsheet này
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTodoBottomSheet(
    todo: Todo? = null, //nếu todo truyền vào là null -> thêm, khác null -> sửa
    viewModel: EditTodoViewModel = hiltViewModel(),
    onDismiss: () -> Unit, //thực hiện khi bottomsheet bị ẩn
    snackbarHostState: SnackbarHostState,
) {

    //các thuộc tính của Todo
    var dateTime by remember { mutableStateOf(if (todo != null) todo.dateTime else LocalDateTime.now()) }
    var inputTodoTitle by remember { mutableStateOf(if (todo != null) todo.title else "") }
    var inputTodoDescription by remember { mutableStateOf(if (todo != null) todo.description else "") }
    var selectedImportance by remember {
        mutableStateOf(if (todo != null) todo.importanceLevel else ImportanceLevel.NORMAL)
    }
    var reminderEnabled by remember { mutableStateOf(if (todo != null) todo.reminderEnabled else true) }

    //Ẩn hoặc hiện bottomsheet chọn ngày và giờ
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

    //focus cho textfield
    val focusRequester = remember { FocusRequester() }

    //cần cho bottomsheet để có thể hiển thị được
    //đổi thành false nếu muốn bottomsheet hiển thị phân nữa, true thì hiển thị full
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = Modifier.height(300.dp), //giới hạn chiều cao
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = {}, //xóa bỏ icon ở giữa bottomsheet
    ) {

        //LaunchedEffect có key là Unit sẽ được gọi 1 lần khi bottomsheet được compose lại
        LaunchedEffect(Unit) {
            focusRequester.requestFocus() //yêu cầu focus vào textfield
            if (todo == null) {
                //nếu todo = null -> thêm todo mới, truyền thời gian hiện tại vào
                dateTime = LocalDateTime.now()
            }
        }

        //dùng Box để hiển thị các thành phần xếp chồng lên nhau
        //trongg trường hợp này là thanh các action sẽ xếp chồng lên chổ chứa các text field
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {
            //hiển thị label ngày giờ và 2 textfield
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

            //Hiển thị thanh các action
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
                        painter = painterResource(R.drawable.calendar_alt),
                        contentDescription = null
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
                                    reminderEnabled = reminderEnabled
                                )
                            )
                            viewModel.showUpdatedTodoSnackbar(
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
                                    reminderEnabled = reminderEnabled
                                )
                            )
                            viewModel.showAddedTodoSnackbar(
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
                        painter = painterResource(R.drawable.check),
                        contentDescription = null
                    )
                }
            }
        }
    }
}