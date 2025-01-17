package com.brighttorchstudio.schedist.ui.shared_view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == "manage_todo_screen",
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
                )
            },
            label = { Text("Home") },
            onClick = {
                if (navController.currentDestination?.route != "manage_todo_screen") {
                    navController.navigate("manage_todo_screen")
                }
            }
        )
    }
}