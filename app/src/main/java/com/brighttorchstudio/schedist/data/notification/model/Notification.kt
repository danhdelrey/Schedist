package com.brighttorchstudio.schedist.data.notification.model

import java.time.LocalDateTime


data class Notification(
    val id: String,
    val title: String,
    val description: String,
    val scheduledDateTime: LocalDateTime,
)
