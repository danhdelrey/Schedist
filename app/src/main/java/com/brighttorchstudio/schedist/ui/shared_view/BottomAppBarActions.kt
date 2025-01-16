package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun BottomAppBarActions(
    onActionPerformed: () -> Unit
) {
    BottomAppBar(
        actions = {
            TextButton(
                onClick = {
                    onActionPerformed()
                }
            ) {
                Text("Delete")
            }
        }
    )
}