package com.brighttorchstudio.schedist.ui.features.schedule_todo.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.brighttorchstudio.schedist.ui.shared_view.DatePickerDocked

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTodoBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxSize(),
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
            },
            dragHandle = {},
        ) {
            DatePickerDocked()
        }
    }
}