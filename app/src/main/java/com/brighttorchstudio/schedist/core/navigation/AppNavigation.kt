package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.ui.features.manage_note.view.ManageNoteScreen
import com.brighttorchstudio.schedist.ui.features.manage_profile.view.ManageProfileScreen
import com.brighttorchstudio.schedist.ui.features.manage_tag.view.ManageTagScreen
import com.brighttorchstudio.schedist.ui.features.manage_todo.view.ManageTodoScreen
import com.brighttorchstudio.schedist.ui.features.update_tag.view.UpdateTagScreen
import kotlinx.serialization.json.Json

//Là điểm vào đầu tiên của ứng dụng
//Chứa các route của các screen để điều hướng
@Composable
fun AppNavigation() {
    val navController =
        rememberNavController() //truyền cho các screen để nó có thể dùng để điều hướng sang screen khác
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
        composable(
            route = AppScreens.ProfileScreen.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ManageProfileScreen(navController = navController)
        }


        composable(
            route = AppScreens.ManageTagScreen.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            }
        ) {
            ManageTagScreen(navController = navController)
        }

        composable(
            route = AppScreens.UpdateTagScreen.route,
            arguments = listOf(
                navArgument("tagJson") { type = NavType.StringType }
            ),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(500)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )
            }
        ) { backStackEntry ->
            val tagJson = backStackEntry.arguments?.getString("tagJson")
            val tag = if (tagJson.isNullOrEmpty()) {
                null
            } else {
                Json.decodeFromString<Tag>(tagJson)
            }
            UpdateTagScreen(
                navController = navController,
                tag = tag
            )
        }
    }
}