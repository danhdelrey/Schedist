package com.brighttorchstudio.schedist

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brighttorchstudio.schedist.ui.features.todo_management.view.TodoManagementScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "todo_management_screen") {
        composable(
            route = "todo_management_screen",
        ) {
            TodoManagementScreen(navController = navController)
        }

    }
}