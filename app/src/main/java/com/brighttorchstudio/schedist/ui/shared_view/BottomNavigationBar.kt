package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue


@Composable
fun BottomNavigationBar(
    onChangedIndex: (Int) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }
    NavigationBar {
        NavigationBarItem(
            selected = currentIndex == 0,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
                )
            },
            label = { Text("Home") },
            onClick = {
                currentIndex = 0
                onChangedIndex(0)
            }
        )
    }
}