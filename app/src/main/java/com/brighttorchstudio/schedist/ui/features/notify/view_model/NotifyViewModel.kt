package com.brighttorchstudio.schedist.ui.features.notify.view_model

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.brighttorchstudio.schedist.data.notification.model.NotificationModel
import com.brighttorchstudio.schedist.data.notification.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class NotifyViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val localNotificationRepository: NotificationRepository
) : ViewModel() {

    sealed class NotificationPermissionState {
        object GrantedAndEnabled : NotificationPermissionState()
        object GrantedButDisabled : NotificationPermissionState()
        object Denied : NotificationPermissionState()
    }

    private val _notificationPermissionState = MutableStateFlow<NotificationPermissionState>(
        NotificationPermissionState.GrantedAndEnabled
    )
    val notificationPermissionState: StateFlow<NotificationPermissionState> =
        _notificationPermissionState.asStateFlow()

    init {
        checkNotificationPermissionState()
    }


    fun checkNotificationPermissionState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                    _notificationPermissionState.value =
                        NotificationPermissionState.GrantedAndEnabled
                } else {
                    _notificationPermissionState.value =
                        NotificationPermissionState.GrantedButDisabled
                }
            } else {
                _notificationPermissionState.value = NotificationPermissionState.Denied
            }
        } else {
            // On pre-Tiramisu, only check if notifications are enabled:
            if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                _notificationPermissionState.value = NotificationPermissionState.GrantedAndEnabled
            } else {
                _notificationPermissionState.value = NotificationPermissionState.GrantedButDisabled
            }
        }
    }

    fun scheduleNotification(
        notification: NotificationModel,
        duration: Long,
        timeUnit: TimeUnit,
    ) {
        localNotificationRepository.scheduleNotification(
            notification = notification,
            duration = duration,
            timeUnit = timeUnit
        )
    }
}