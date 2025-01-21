package com.brighttorchstudio.schedist.data.notification.repository

import com.brighttorchstudio.schedist.data.notification.model.NotificationModel
import java.util.concurrent.TimeUnit

interface NotificationRepository {
    fun scheduleNotification(
        notification: NotificationModel,
        duration: Long,
        timeUnit: TimeUnit
    )

}