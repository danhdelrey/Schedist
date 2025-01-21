package com.brighttorchstudio.schedist.data.notification.repository

import com.brighttorchstudio.schedist.core.common.NotificationPermissionState
import com.brighttorchstudio.schedist.data.notification.model.NotificationModel
import java.util.concurrent.TimeUnit

interface NotificationRepository {
    fun checkNotificationPermissionState(): NotificationPermissionState
    fun scheduleNotification(
        notification: NotificationModel,
        duration: Long,
        timeUnit: TimeUnit
    )

}