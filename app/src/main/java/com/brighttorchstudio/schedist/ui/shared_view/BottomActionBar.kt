package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable

// Hiển thị bottom app bả chứa các hành động
// hiện tại chỉ có 1 hành động
@Composable
fun BottomActionBar(
    action1: @Composable () -> Unit = {},
) {
    BottomAppBar(
        actions = {
            action1()
        }
    )
}