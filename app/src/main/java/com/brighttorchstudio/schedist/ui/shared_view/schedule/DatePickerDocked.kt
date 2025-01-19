package com.brighttorchstudio.schedist.ui.shared_view.schedule

import androidx.compose.foundation.background
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import java.time.LocalDate

//Hiển thị giao diện chọn ngày (lịch)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    initialDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit, //truyền date được chọn ra bên ngoài
) {
    //cần thiết để hiển thị date picker
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateTimeHelper.localDateToMillis(initialDate)
    )

    //Mỗi khi date bị thay đổi thì thực hiện việc truyền thời gian đó ra bên ngoài
    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis != null) {
            val selectedLocalDate =
                DateTimeHelper.millisToLocalDate(datePickerState.selectedDateMillis!!)
            if (selectedLocalDate != initialDate) { // Kiểm tra xem ngày đã thực sự thay đổi chưa
                onDateSelected(selectedLocalDate)
            }
        }
    }

    //giao diện date picker của material 3
    DatePicker(
        state = datePickerState,
        showModeToggle = false,
        title = null,
        headline = null,
        modifier = Modifier
            .scale(0.9f)
            .background(
                MaterialTheme.colorScheme.surfaceContainerLowest,
                shape = MaterialTheme.shapes.small
            )

    )


}