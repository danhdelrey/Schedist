package com.brighttorchstudio.schedist.ui.features.todo_management.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.config.theme.SchedistTheme
import com.brighttorchstudio.schedist.data.todo.model.Todo
import com.brighttorchstudio.schedist.helpers.DateTimeHelper
import java.time.LocalDateTime

@Composable
fun TodoItem(todo: Todo) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .background(MaterialTheme.colorScheme.surfaceContainerLow, MaterialTheme.shapes.small)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            )
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                RadioButton(selected = false, onClick = { /*TODO*/ })
                Column(
                    modifier = Modifier
                        .weight(1f)

                ) {
                    Text(
                        text = todo.title,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth()


                    )
                    Row {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Calendar",
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = DateTimeHelper.formatLocalDateTime(todo.dateTime),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                IconToggleButton(
                    checked = false,
                    onCheckedChange = { /*TODO*/ }
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Calendar",
                        tint = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 14.dp)
            ) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                if (!todo.description.isNullOrEmpty())
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

@Preview(showBackground = true)
@Composable
fun TodoItemPreview() {
    SchedistTheme {
        val todo = Todo(
            id = "1",
            title = "Đem đồ ăn cho chồng cute đáng yêu nhất thế giới",
            description = "ở toàn nhà ABC phía trái của hẻm 45, gửi xe tốn 5k, đưa cơm xong hỏi nó mật khẩu két sắt luôn",
            priority = 1,
            dateTime = LocalDateTime.now(),
            reminderEnabled = false,
        )
        TodoItem(todo)
    }
}