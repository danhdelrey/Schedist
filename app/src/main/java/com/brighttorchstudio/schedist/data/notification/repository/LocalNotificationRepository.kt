package com.brighttorchstudio.schedist.data.notification.repository

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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