package com.brighttorchstudio.schedist.data.services.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.brighttorchstudio.schedist.core.helpers.NotificationHelper

class NotificationWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        val notificationID = inputData.getString("notification_id")
        val notificationTitle = inputData.getString("notification_title")
        val notificationDescription = inputData.getString("notification_description")

        NotificationHelper.pushNotification(
            context = applicationContext,
            title = notificationTitle,
            description = notificationDescription,
            id = notificationID.hashCode()
        )

        return Result.success()
    }

}