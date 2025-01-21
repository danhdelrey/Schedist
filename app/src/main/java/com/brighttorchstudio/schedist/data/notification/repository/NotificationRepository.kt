package com.brighttorchstudio.schedist.data.notification.repository

import com.brighttorchstudio.schedist.core.common.NotificationPermissionState
import com.brighttorchstudio.schedist.data.notification.model.Notification
import java.util.concurrent.TimeUnit

interface NotificationRepository {
    fun checkNotificationPermissionState(): NotificationPermissionState
    fun scheduleNotification(
        notification: Notification,
        duration: Long,
        timeUnit: TimeUnit
    )

}