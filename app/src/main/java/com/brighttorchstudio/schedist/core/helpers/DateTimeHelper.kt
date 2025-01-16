package com.brighttorchstudio.schedist.core.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
    companion object {
        fun formatLocalDateTime(dateTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
            return dateTime.format(formatter)
        }

        fun isDue(dateTime: LocalDateTime): Boolean {
            return dateTime.isBefore(LocalDateTime.now())
        }
    }
}