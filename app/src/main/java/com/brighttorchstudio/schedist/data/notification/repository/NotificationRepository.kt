package com.brighttorchstudio.schedist.data.notification.repository

import com.brighttorchstudio.schedist.data.notification.model.Notification

interface NotificationRepository {
    fun scheduleNotification(
        notification: Notification,
    )

    fun cancelNotification(notificationId: String)


}