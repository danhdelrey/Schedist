package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
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
            selected = navController.currentDestination?.route == AppScreens.ManageTodoScreen.route,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "home"
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
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "home"
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