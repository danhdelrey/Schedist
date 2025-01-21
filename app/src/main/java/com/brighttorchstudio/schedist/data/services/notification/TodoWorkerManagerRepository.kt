package com.brighttorchstudio.schedist.data.services.notification

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TodoWorkerManagerRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val todoDao: TodoDao,
) : WorkManagerRepository {

    private val workManager = WorkManager.getInstance(context)

    override suspend fun scheduleReminder(
        duration: Long,
        timeUnit: TimeUnit,
        id: String,
    ) {
        val todo = todoDao.getTodoById(id)

        val data = Data.Builder()
        data.putString("todo_title", todo.title)
        data.putString("todo_description", todo.description)

        val workRequestBuilder = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(duration, timeUnit)
            .setInputData(data.build())
            .build()

        workManager.enqueueUniqueWork(
            id,
            ExistingWorkPolicy.REPLACE,
            workRequestBuilder
        )
    }
}