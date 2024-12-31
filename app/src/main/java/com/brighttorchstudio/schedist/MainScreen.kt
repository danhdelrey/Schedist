package com.brighttorchstudio.schedist

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.brighttorchstudio.schedist.ui.features.todo_management.view.TodoManagementScreen

@Composable
fun MainScreen(navController: NavHostController) {
    var currentIndex by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentIndex == 0,
                    icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "home") },
                    label = { Text("Home") },
                    onClick = { if(currentIndex!=0) currentIndex = 0 }
                )
//                NavigationBarItem(
//                    selected = currentIndex == 1,
//                    icon = { Icon(imageVector = Icons.Default.Search, contentDescription = "search") },
//                    label = { Text("Home") },
//                    onClick = { if(currentIndex!=1) currentIndex = 1 }
//                )
//                NavigationBarItem(
//                    selected = currentIndex == 2,
//                    icon = { Icon(imageVector = Icons.Default.AccountCircle, contentDescription = "profile") },
//                    label = { Text("Home") },
//                    onClick = { if(currentIndex!=2) currentIndex = 2 }
//                )
            }
        }
    ) { innerPadding ->
        when(currentIndex) {
            0 -> {
                TodoManagementScreen(innerPadding = innerPadding, navController = navController)
            }
//            1 -> {
//                // SearchScreen()
//            }
//            2 -> {
//                // ProfileScreen()
//            }
        }

    }
}