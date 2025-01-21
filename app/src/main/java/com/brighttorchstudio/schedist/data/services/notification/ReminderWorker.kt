package com.brighttorchstudio.schedist.data.services.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {
        val todoTitle = inputData.getString("todo_title")
        val todoDescription = inputData.getString("todo_description")

        WorkerHelper.makeReminderNotification(
            context = applicationContext,
            notificationTitle = todoTitle,
            notificationMessage = todoDescription,
        )


        return Result.success()
    }

}