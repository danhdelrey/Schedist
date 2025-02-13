package com.brighttorchstudio.schedist.ui.features.edit_todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.todo.model.Subtask
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.edit_todo.view_model.EditTodoViewModel
import com.brighttorchstudio.schedist.ui.shared_view.ImportanceDropdownButton
import com.brighttorchstudio.schedist.ui.shared_view.StyledTextField
import com.brighttorchstudio.schedist.ui.shared_view.schedule.FormattedTimeText
import com.brighttorchstudio.schedist.ui.shared_view.schedule.ScheduleBottomSheet
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

//Hiển thị một bottomsheet để thêm hoặc sửa một todo
//phải remember một boolean ở bên ngoài để ẩn hoặc hiện bottomsheet này
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
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
        modifier = Modifier.fillMaxHeight(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
            viewModel.onCancelCLicked()
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
            else{
                viewModel.setSubtaskList(todo.subtasks)
            }
        }

        val scrollState = rememberScrollState()
        val focusManager = LocalFocusManager.current

        //dùng Box để hiển thị các thành phần xếp chồng lên nhau
        //trongg trường hợp này là thanh các action sẽ xếp chồng lên chổ chứa các text field
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp)
        ) {

                //hiển thị label ngày giờ và 2 textfield
                Column() {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        IconButton(
                            onClick = {
                                onDismiss()
                                viewModel.onCancelCLicked()}
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.chevron_left),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }

                        FormattedTimeText(dateTime)

                        IconButton(
                            onClick = {
                                focusManager.clearFocus()
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
                                            reminderEnabled = reminderEnabled,
                                            subtasks = emptyList(),
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
                                            reminderEnabled = reminderEnabled,
                                            subtasks = emptyList(),
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

                    Column(modifier = Modifier
                        .weight(1f)
                        ) {
                        Column(modifier = Modifier
                            .verticalScroll(scrollState)) {


                    StyledTextField(
                        value = inputTodoTitle,
                        onValueChange = { inputTodoTitle = it },
                        placeholderText = "Tên nhiệm vụ",
                        textStyle = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        modifier = Modifier.focusRequester(focusRequester)
                    )


                        BasicTextField(
                            value = inputTodoDescription,
                            onValueChange = { inputTodoDescription = it },
                            modifier = Modifier
                                .fillMaxWidth(),
                            textStyle = MaterialTheme.typography.bodyLarge,
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier.padding(horizontal = 20.dp)
                                ){
                                    if (inputTodoDescription.isEmpty()) {
                                        Text(
                                            text = "Nhập mô tả nhiệm vụ...",
                                            style = MaterialTheme.typography.bodyLarge,
                                            color = MaterialTheme.colorScheme.outline)
                                    }
                                    innerTextField()
                                }
                            }
                        )
                            SubtaskEditView()
                    }}

                    //Hiển thị thanh các action
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
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
                    }

            }
        }
    }
}