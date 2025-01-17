package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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

    Box(

    ) {
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
            ),
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
        ) {
            Row(

            ) {
                Text(selectedItem.getDisplayName(), color = Color.White)
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                    contentDescription = "dropdown",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                )
            }
        }
    }
}