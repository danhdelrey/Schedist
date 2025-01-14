package com.brighttorchstudio.schedist.helpers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeHelper {
    companion object {
        fun formatLocalDateTime(dateTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
            return dateTime.format(formatter)
        }
        //other static functions
    }
}