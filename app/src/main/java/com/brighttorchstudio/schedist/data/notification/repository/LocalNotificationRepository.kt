package com.brighttorchstudio.schedist.data.notification.repository

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.brighttorchstudio.schedist.core.common.NotificationPermissionState
import com.brighttorchstudio.schedist.core.helpers.DateTimeHelper
import com.brighttorchstudio.schedist.data.notification.model.Notification
import com.brighttorchstudio.schedist.data.services.workers.NotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.abs

class LocalNotificationRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun checkNotificationPermissionState(): NotificationPermissionState {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                    NotificationPermissionState.GRANTED_AND_ENABLED
                } else {
                    NotificationPermissionState.GRANTED_BUT_DISABLED
                }
            } else {
                NotificationPermissionState.DENIED
            }
        } else {
            // On pre-Tiramisu, only check if notifications are enabled:
            return if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                NotificationPermissionState.GRANTED_AND_ENABLED
            } else {
                NotificationPermissionState.GRANTED_BUT_DISABLED
            }
        }
    }


    override fun scheduleNotification(
        notification: Notification,
    ) {
        val secondDifference =
            DateTimeHelper.calculateSecondsDifference(notification.scheduledDateTime)
        if (secondDifference < 0) {
            val data = Data.Builder()
            data.putString("notification_id", notification.id)
            data.putString("notification_title", notification.title)
            data.putString("notification_description", notification.description)

            val workRequestBuilder = OneTimeWorkRequestBuilder<NotificationWorker>()
                .setInitialDelay(abs(secondDifference), TimeUnit.SECONDS)
                .setInputData(data.build())
                .build()

            workManager.enqueueUniqueWork(
                notification.id,
                ExistingWorkPolicy.REPLACE,
                workRequestBuilder
            )
        }

    }

    override fun cancelNotification(notificationId: String) {
        workManager.cancelUniqueWork(notificationId)
    }


}