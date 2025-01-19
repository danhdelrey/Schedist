package com.brighttorchstudio.schedist.core.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.brighttorchstudio.schedist.R

//Là cái thanh điều hướng ở dưới màn hình, mỗi screen sẽ có để dùng để điều hướng sang screen khác
@Composable
fun BottomNavigationBar(
    navController: NavHostController,
) {
    NavigationBar {
        NavigationBarItem(
            selected = navController.currentDestination?.route == AppScreens.ManageTodoScreen.route, //kiểm tra xem route hiện tại có phải là của screen đang được hiển thị không
            icon = {
                Icon(
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = null
                )
            },
            label = { Text("Home") },
            onClick = {
                //nếu route hiện tại không trùng với route của screen này thì mới có thể navigate sang screen này, tránh trường hợp điều hướng đến chính bản thân nó
                if (navController.currentDestination?.route != AppScreens.ManageTodoScreen.route) {
                    navController.navigate(AppScreens.ManageTodoScreen.route) {
                        //navigate xong thì xóa bỏ các screen trước đó không cho quay lại, nếu quay lại thì thoát app, vì những screen này được xem là các screen đầu tiên của ứng dụng
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