package com.brighttorchstudio.schedist.ui.features.notify.view

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
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
    val showDialog by viewModel.showPermissionDialog.collectAsStateWithLifecycle()
    val context = LocalContext.current

    when (permissionState) {
        is NotifyViewModel.PermissionState.GrantedAndEnabled -> {

        }

        is NotifyViewModel.PermissionState.GrantedButDisabled -> {
            if (showDialog) {
                AlertDialog(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.bell),
                            contentDescription = "Example Icon"
                        )
                    },
                    title = {
                        Text(text = "Cho phép thông báo")
                    },
                    text = {
                        Text(text = "Ứng dụng cần quyền truy cập để gửi thông báo nhắc nhở về các nhiệm vụ của bạn. Vui lòng bật thông báo trong cài đặt để sử dụng tính năng này.")
                    },
                    onDismissRequest = {
                        viewModel.hideDialog()
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.openNotificationSettings(context)
                                viewModel.hideDialog()
                            }
                        ) {
                            Text("Mở cài đặt")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.hideDialog()
                            }
                        ) {
                            Text("Hủy")
                        }
                    }
                )
            }
        }

        is NotifyViewModel.PermissionState.Denied -> {
            if (showDialog) {
                AlertDialog(
                    icon = {
                        Icon(
                            painter = painterResource(R.drawable.bell),
                            contentDescription = "Example Icon"
                        )
                    },
                    title = {
                        Text(text = "Cho phép thông báo")
                    },
                    text = {
                        Text(text = "Ứng dụng cần quyền truy cập để gửi thông báo nhắc nhở về các nhiệm vụ của bạn. Vui lòng bật thông báo trong cài đặt để sử dụng tính năng này.")
                    },
                    onDismissRequest = {
                        viewModel.hideDialog()
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                viewModel.openNotificationSettings(context)
                                viewModel.hideDialog()
                            }
                        ) {
                            Text("Mở cài đặt")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.hideDialog()
                            }
                        ) {
                            Text("Hủy")
                        }
                    }
                )
            }
        }
    }
}