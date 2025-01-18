package com.brighttorchstudio.schedist.ui.shared_view.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleBottomSheet(
    onDismiss: () -> Unit,
    initialDateTime: LocalDateTime,
    reminderEnabled: Boolean,
    onSubmitted: (LocalDateTime, Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var date by remember { mutableStateOf(initialDateTime.toLocalDate()) }
    var time by remember { mutableStateOf(initialDateTime.toLocalTime()) }
    var currentReminderEnabled by remember { mutableStateOf(reminderEnabled) }

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
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(
                    modifier = Modifier.weight(1f)
                )
                IconButton(
                    onClick = {
                        onSubmitted(LocalDateTime.of(date, time), currentReminderEnabled)
                        onDismiss()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.check),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
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
                        painter = painterResource(R.drawable.clock),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
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
                    Spacer(
                        modifier = Modifier
                            .width(5.dp)
                    )
                    Icon(
                        painter = painterResource(R.drawable.chevron_right),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(15.dp)
                            .align(Alignment.CenterVertically)
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
                        painter = painterResource(R.drawable.bell),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(
                        modifier = Modifier
                            .width(10.dp)
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
                        checked = currentReminderEnabled,
                        onCheckedChange = {
                            currentReminderEnabled = it
                        },
                        modifier = Modifier
                            .height(24.dp)
                            .scale(0.9f)
                    )
                }
            }


        }
    }
}