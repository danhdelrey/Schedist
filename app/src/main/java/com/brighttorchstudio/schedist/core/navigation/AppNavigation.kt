package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brighttorchstudio.schedist.ui.features.manage_todo.view.ManageTodoScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "manage_todo_screen") {
        composable(
            route = "manage_todo_screen",
        ) {
            ManageTodoScreen(navController = navController)
        }

    }
}