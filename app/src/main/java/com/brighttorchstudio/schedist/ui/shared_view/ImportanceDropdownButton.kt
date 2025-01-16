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
import com.brighttorchstudio.schedist.core.common.ImportanceLevel


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