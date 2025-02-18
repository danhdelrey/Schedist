package com.brighttorchstudio.schedist.ui.features.manage_profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.core.navigation.AppScreens
import com.brighttorchstudio.schedist.core.navigation.BottomNavigationBar

@Composable
fun ManageProfileScreen(
    navController: NavHostController
) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Button(
            modifier = Modifier.padding(innerPadding),
            onClick = {
                navController.navigate(AppScreens.ManageTagScreen.route)
            }
        ) {
            Text(text = "Go to manage tag screen")
        }
    }
}