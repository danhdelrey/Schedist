package com.brighttorchstudio.schedist.data.notification.repository

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.brighttorchstudio.schedist.data.notification.model.NotificationModel
import com.brighttorchstudio.schedist.data.services.workers.NotificationWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocalNotificationRepository @Inject constructor(
    @ApplicationContext private val context: Context,
) : NotificationRepository {

    private val workManager = WorkManager.getInstance(context)

    override fun scheduleNotification(
        notification: NotificationModel,
        duration: Long,
        timeUnit: TimeUnit
    ) {

        val data = Data.Builder()
        data.putString("notification_id", notification.id)
        data.putString("notification_title", notification.title)
        data.putString("notification_description", notification.description)

        val workRequestBuilder = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setInitialDelay(duration, timeUnit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            notification.id,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )
    }


}