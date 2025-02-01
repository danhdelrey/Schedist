package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brighttorchstudio.schedist.ui.features.manage_note.view.ManageNoteScreen
import com.brighttorchstudio.schedist.ui.features.manage_todo.view.ManageTodoScreen

//Là điểm vào đầu tiên của ứng dụng
//Chứa các route của các screen để điều hướng
@Composable
fun AppNavigation() {
    val navController = rememberNavController() //truyền cho các screen để nó có thể dùng để điều hướng sang screen khác
    NavHost(navController = navController, startDestination = AppScreens.ManageTodoScreen.route) {
        composable(
            route = AppScreens.ManageTodoScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ManageTodoScreen(navController = navController)
        }
        composable(
            route = AppScreens.ManageNoteScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ManageNoteScreen(navController = navController)
        }

        //Khai báo các route khác ở đây...
    }
}