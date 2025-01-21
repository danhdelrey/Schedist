package com.brighttorchstudio.schedist.data.services.notification

import java.util.concurrent.TimeUnit


interface WorkManagerRepository {
    suspend fun scheduleReminder(
        duration: Long,
        timeUnit: TimeUnit,
        id: String,
    )
}