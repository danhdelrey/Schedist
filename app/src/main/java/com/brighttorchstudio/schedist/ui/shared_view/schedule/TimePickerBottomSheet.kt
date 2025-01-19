package com.brighttorchstudio.schedist.ui.shared_view.schedule

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalTime


//Hiển thị bottomsheet chọn thời gian
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerBottomSheet(
    initialTime: LocalTime, //giờ ban đầu
    onDismiss: () -> Unit, //thực hiện khi bottomsheet bị ẩn
    onConfirm: (LocalTime) -> Unit //truyền thời gian đã được chọn ra bên ngoài
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    //cần thiết cho time picker
    val timePickerState = rememberTimePickerState(
        initialHour = initialTime.hour,
        initialMinute = initialTime.minute,
        is24Hour = true,
    )

    //Hiển thị bottomsheet
    ModalBottomSheet(
        modifier = Modifier.height(200.dp),
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
                        onConfirm(
                            LocalTime.of(
                                timePickerState.hour,
                                timePickerState.minute
                            )
                        )
                        onDismiss()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Date",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            //time picker của material 3
            TimeInput(
                state = timePickerState,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}