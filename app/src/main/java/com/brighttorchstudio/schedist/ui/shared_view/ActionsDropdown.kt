package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.brighttorchstudio.schedist.R

@Composable
fun ActionsDropdown(
    onDeleteActionRequested: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(

    ) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(-(20.dp), 0.dp)
        ) {
            DropdownMenuItem(
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.trash_alt),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error
                    )
                },
                text = {
                    Text(
                        text = "Xóa nhãn",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                onClick = {
                    expanded = false
                    onDeleteActionRequested()
                },

                )
        }
        IconButton(
            onClick = {
                expanded = !expanded
            }
        ) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = "menu"
            )
        }
    }
}