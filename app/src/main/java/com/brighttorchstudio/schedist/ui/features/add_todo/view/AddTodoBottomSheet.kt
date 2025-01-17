package com.brighttorchstudio.schedist.ui.features.add_todo.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.ui.shared_view.FormattedTimeText
import com.brighttorchstudio.schedist.ui.shared_view.ImportanceDropdownButton
import com.brighttorchstudio.schedist.ui.shared_view.StyledTextField
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoBottomSheet(
    showBottomSheet: Boolean,
    onDismiss: () -> Unit,
) {
    var dateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var inputTodoTitle by remember { mutableStateOf("") }
    var inputTodoDescription by remember { mutableStateOf("") }
    var selectedImportance by remember {
        mutableStateOf(ImportanceLevel.NORMAL)
    }


    val focusRequester = remember { FocusRequester() }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.height(300.dp),
            sheetState = sheetState,
            onDismissRequest = {
                onDismiss()
                // Reset các giá trị khi đóng BottomSheet
                inputTodoTitle = ""
                inputTodoDescription = ""
                selectedImportance = ImportanceLevel.NORMAL
            },
            dragHandle = {},
        ) {

            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                dateTime = LocalDateTime.now()
            }


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ) {
                Column {
                    FormattedTimeText(dateTime)

                    StyledTextField(
                        value = inputTodoTitle,
                        onValueChange = { inputTodoTitle = it },
                        placeholderText = "Tên nhiệm vụ",
                        textStyle = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        modifier = Modifier.focusRequester(focusRequester)
                    )

                    StyledTextField(
                        value = inputTodoDescription,
                        onValueChange = { inputTodoDescription = it },
                        placeholderText = "Nhập mô tả cho nhiệm vụ này...",
                        textStyle = MaterialTheme.typography.bodyLarge,
                        maxLines = 3
                    )


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                ) {
                    ImportanceDropdownButton(
                        initialSelectedItem = selectedImportance,
                        onSelectedOption = { newImportance ->
                            selectedImportance = newImportance
                        }
                    )
                }
            }
        }
    }
}