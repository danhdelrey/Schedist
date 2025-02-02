package com.brighttorchstudio.schedist.ui.shared_view.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit,
    initialDate: LocalDate,
    clearDate : () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = DateTimeHelper.localDateToMillis(initialDate))

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {

        },
        dismissButton = {

        }
    ) {
        DatePicker(
            state = datePickerState,
            showModeToggle = false,
            title = null,
            headline = null)

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()){

            TextButton(onClick = clearDate) {
                Text("CLEAR")
            }
            Row {
                TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
                TextButton(onClick = {
                    onDateSelected(DateTimeHelper.millisToLocalDate(datePickerState.selectedDateMillis!!))
                    onDismiss()
                }) {
                    Text("OK")
                }

            }


        }
    }
}