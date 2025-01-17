package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.material3.BottomAppBar
import androidx.compose.runtime.Composable

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