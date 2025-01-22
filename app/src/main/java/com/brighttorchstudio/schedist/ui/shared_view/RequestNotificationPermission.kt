package com.brighttorchstudio.schedist.ui.shared_view

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationManagerCompat

@Composable
fun RequestNotificationPermission() {
    val context = LocalContext.current
    var permission by remember {
        mutableStateOf(
            NotificationManagerCompat.from(context).areNotificationsEnabled()
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permission =
                isGranted || NotificationManagerCompat.from(context).areNotificationsEnabled()
        }
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        // For Android 13 and above
        LaunchedEffect(Unit) {
            if (!permission) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    } else {
        // For Android 12 and below, permissions are granted at install time, so just check the current state
        LaunchedEffect(Unit) {
            permission = NotificationManagerCompat.from(context).areNotificationsEnabled()
        }

    }

}