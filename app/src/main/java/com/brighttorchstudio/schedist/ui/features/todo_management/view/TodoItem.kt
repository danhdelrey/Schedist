package com.brighttorchstudio.schedist.ui.features.todo_management.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.config.custom_colors.importantTask
import com.brighttorchstudio.schedist.config.custom_colors.normalTask
import com.brighttorchstudio.schedist.config.custom_colors.trivialTask
import com.brighttorchstudio.schedist.config.custom_colors.veryImportantTask
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.ui.features.todo_management.view_model.TodoManagementViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    todo: Todo,
    viewModel: TodoManagementViewModel,
    isSelected: Boolean,
    onToggleSelection: () -> Unit,
    onClick: () -> Unit
) {
    val isSelectionMode by viewModel.isSelectionMode.collectAsStateWithLifecycle()
    var showTodoDetails by remember { mutableStateOf(false) }
    var isDue = DateTimeHelper.isDue(todo.dateTime)

    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .background(
                MaterialTheme.colorScheme.surfaceContainerLow,
                MaterialTheme.shapes.small
            )
            .border(
                width = if (isDue) 2.dp else 0.dp,
                color = if (isDue) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = MaterialTheme.shapes.small
            )
            .combinedClickable(
                onClick = {
                    if (isSelectionMode) {
                        onToggleSelection()
                    } else {
                        onClick()
                    }
                },
                onLongClick = {
                    if (!isSelectionMode) {
                        viewModel.enterSelectionMode()
                        onToggleSelection()
                    }
                }
            )

    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {

                if (isSelectionMode) {
                    Checkbox(checked = isSelected, onCheckedChange = { onToggleSelection() })
                } else {
                    RadioButton(selected = false, onClick = { /*TODO*/ })
                }


                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            modifier = Modifier.size(20.dp),
                            tint = when (todo.priority) {
                                1 -> veryImportantTask
                                2 -> importantTask
                                3 -> normalTask
                                else -> trivialTask
                            }
                        )
                        Text(
                            text = DateTimeHelper.formatLocalDateTime(todo.dateTime),
                            style = MaterialTheme.typography.labelLarge,
                            color = when (todo.priority) {
                                1 -> veryImportantTask
                                2 -> importantTask
                                3 -> normalTask
                                else -> trivialTask
                            }
                        )
                    }
                }


                IconToggleButton(
                    checked = showTodoDetails,
                    onCheckedChange = { showTodoDetails = it }
                ) {
                    Icon(
                        imageVector = if (showTodoDetails) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Expand details",
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
            AnimatedVisibility(visible = showTodoDetails) {
                Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    if (!todo.description.isNullOrEmpty()) {
                        Text(
                            text = todo.description,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }

        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TodoItemPreview() {
//    SchedistTheme {
//        val todo = Todo(
//            id = "1",
//            title = "Đem đồ ăn cho chồng cute đáng yêu nhất thế giới",
//            description = "ở toàn nhà ABC phía trái của hẻm 45, gửi xe tốn 5k, đưa cơm xong hỏi nó mật khẩu két sắt luôn",
//            priority = 1,
//            dateTime = LocalDateTime.now(),
//            reminderEnabled = false,
//        )
//        TodoItem(todo, {}, false, {}, {})
//    }
//}