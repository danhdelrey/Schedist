package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.config.custom_colors.importantTask
import com.brighttorchstudio.schedist.config.custom_colors.normalTask
import com.brighttorchstudio.schedist.config.custom_colors.trivialTask
import com.brighttorchstudio.schedist.config.custom_colors.veryImportantTask

enum class ImportanceLevel(val color: Color) {
    VERY_IMPORTANT(veryImportantTask),
    IMPORTANT(importantTask),
    NORMAL(normalTask),
    NOT_IMPORTANT(trivialTask);

    fun getDisplayName(): String {
        return when (this) {
            VERY_IMPORTANT -> "Rất quan trọng"
            IMPORTANT -> "Quan trọng"
            NORMAL -> "Bình thường"
            NOT_IMPORTANT -> "Không quan trọng"
        }
    }
}


@Composable
fun ImportanceDropdownButton(
    initialSelectedItem: ImportanceLevel = ImportanceLevel.NORMAL,
    onSelectedOption: (ImportanceLevel) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(initialSelectedItem) }

    Column {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            ImportanceLevel.entries.forEach { level ->
                DropdownMenuItem(
                    text = { Text(level.getDisplayName()) },
                    onClick = {
                        selectedItem = level
                        expanded = false
                        onSelectedOption(selectedItem)
                    }
                )
            }
        }
        Button(
            onClick = { expanded = !expanded },
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = selectedItem.color
            )
        ) {
            Text(selectedItem.getDisplayName(), color = Color.White)
        }
    }
}