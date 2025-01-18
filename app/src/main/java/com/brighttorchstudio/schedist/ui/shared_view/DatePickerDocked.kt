package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.background
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(

) {
    //var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()


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