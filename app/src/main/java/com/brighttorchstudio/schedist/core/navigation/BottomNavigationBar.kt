package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R


@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == AppScreens.ManageTodoScreen.route,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = null
                )
            },
            label = { Text("Home") },
            onClick = {
                if (navController.currentDestination?.route != AppScreens.ManageTodoScreen.route) {
                    navController.navigate(AppScreens.ManageTodoScreen.route) {
                        popUpTo(AppScreens.ManageTodoScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == AppScreens.ManageNoteScreen.route,
            icon = {
                Icon(
                    painter = painterResource(R.drawable.sticky_note),
                    contentDescription = null
                )
            },
            label = { Text("Note") },
            onClick = {
                if (navController.currentDestination?.route != AppScreens.ManageNoteScreen.route) {
                    navController.navigate(AppScreens.ManageNoteScreen.route) {
                        popUpTo(AppScreens.ManageNoteScreen.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
        )
    }
}