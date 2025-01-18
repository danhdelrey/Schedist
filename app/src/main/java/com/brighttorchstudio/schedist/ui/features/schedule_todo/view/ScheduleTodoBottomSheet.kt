package com.brighttorchstudio.schedist.ui.features.schedule_todo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.ui.shared_view.DatePickerDocked
import com.brighttorchstudio.schedist.ui.shared_view.TimePickerBottomSheet
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTodoBottomSheet(
    onDismiss: () -> Unit,
    initialDateTime: LocalDateTime,
    onSubmitted: (LocalDateTime) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var date by remember { mutableStateOf(initialDateTime.toLocalDate()) }
    var time by remember { mutableStateOf(initialDateTime.toLocalTime()) }

    var showTimePickerBottomSheet by remember { mutableStateOf(false) }
    if (showTimePickerBottomSheet) {
        TimePickerBottomSheet(
            onDismiss = {
                showTimePickerBottomSheet = false
            },
            initialTime = time,
        ) {
            time = it
        }
    }

    ModalBottomSheet(
        modifier = Modifier.fillMaxSize(),
        sheetState = sheetState,
        onDismissRequest = {
            onDismiss()
        },
        dragHandle = {},
    ) {
        Column {

            Row(
                modifier = Modifier
                    .padding(top = 10.dp, start = 5.dp, end = 5.dp)
            ) {
                IconButton(
                    onClick = onDismiss
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        onSubmitted(LocalDateTime.of(date, time))
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            DatePickerDocked(
                initialDate = date
            ) {
                date = it
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 17.dp)
                    .clip(MaterialTheme.shapes.small)
                    .clickable(
                        onClick = {
                            showTimePickerBottomSheet = true
                        }
                    )
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Thời gian",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)

                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = DateTimeHelper.localTimeToFormattedString(time),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)

                    )
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(
                modifier = Modifier
                    .height(9.dp)
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = 17.dp)
            ) {
                Row(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerLowest,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Nhắc nhở",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        modifier = Modifier.align(Alignment.CenterVertically)

                    )
                    Spacer(
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = true,
                        onCheckedChange = {},
                        modifier = Modifier
                            .height(24.dp)
                            .scale(0.9f)
                    )
                }
            }


        }
    }
}