package com.brighttorchstudio.schedist.ui.features.notify.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.brighttorchstudio.schedist.R
import com.brighttorchstudio.schedist.ui.features.notify.view_model.NotifyViewModel

@Composable
fun NotificationPermissionDialog(
    viewModel: NotifyViewModel = hiltViewModel(),
) {
    val permissionState by viewModel.permissionState.collectAsStateWithLifecycle()
    var showDialog by remember { mutableStateOf(false) }

    when (permissionState) {
        is NotifyViewModel.PermissionState.GrantedAndEnabled -> {
            if (showDialog) {
                AlertDialog(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.bell),
                            contentDescription = "Example Icon"
                        )
                    },
                    title = {
                        Text(text = "Dialog Title")
                    },
                    text = {
                        Text(text = "Dialog Text")
                    },
                    onDismissRequest = {
                        showDialog = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                //setting
                            }
                        ) {
                            Text("Confirm")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Dismiss")
                        }
                    }
                )
            }
        }

        is NotifyViewModel.PermissionState.GrantedButDisabled -> {

        }

        is NotifyViewModel.PermissionState.Denied -> {

        }
    }
}