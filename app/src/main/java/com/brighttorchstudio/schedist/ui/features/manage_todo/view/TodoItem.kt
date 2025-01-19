package com.brighttorchstudio.schedist.ui.features.manage_todo.view

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.ui.features.complete_todo.view.CompleteTodoButton
import com.brighttorchstudio.schedist.ui.features.manage_todo.view_model.ManageTodoViewModel

//Hiển thị một todo
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    viewModel: ManageTodoViewModel = hiltViewModel(),
    todo: Todo,
) {
    //lưu trạng thái hiển thị chi tiết todo
    var showTodoDetails by remember { mutableStateOf(false) }

    //kiểm tra xem todo đã tới hạn hay chưa
    var isDue = DateTimeHelper.isDue(todo.dateTime)

    val isSelectionMode by viewModel.isSelectionMode.collectAsStateWithLifecycle()


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
                    viewModel.onTodoClicked(todo)
                },
                onLongClick = {
                    viewModel.onTodoLongClicked(todo)
                }
            )

    ) {
        Column(modifier = Modifier.padding(vertical = 10.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {

                //khi ở trạng thái selection thì hiển thị Checkbox, còn không thì hiển thị CompleteTodoButton (radio button)
                if (isSelectionMode) {
                    Checkbox(
                        checked = viewModel.isTodoSelectedInSelectionMode(todo),
                        onCheckedChange = {
                            viewModel.onTodoClicked(todo)
                        }
                    )
                } else {
                    CompleteTodoButton(
                        todo = todo,
                        scope = scope,
                        snackBarHostState = snackBarHostState
                    )
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
                            painter = painterResource(R.drawable.calendar_alt),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = todo.importanceLevel.color
                        )
                        Text(
                            text = DateTimeHelper.formatLocalDateTime(todo.dateTime),
                            style = MaterialTheme.typography.labelLarge,
                            color = todo.importanceLevel.color
                        )
                    }
                }


                IconToggleButton(
                    checked = showTodoDetails,
                    onCheckedChange = { showTodoDetails = it }
                ) {
                    Icon(
                        painter = if (showTodoDetails) painterResource(R.drawable.chevron_up) else painterResource(
                            R.drawable.chevron_down
                        ),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }

            //Animation lên xuống mượt mà khi ẩn/hiện chi tiết todo
            AnimatedVisibility(visible = showTodoDetails) {

                //Hiển thị chi tiết todo
                Column(modifier = Modifier.padding(horizontal = 14.dp)) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    if (todo.description.isNotEmpty()) {
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

