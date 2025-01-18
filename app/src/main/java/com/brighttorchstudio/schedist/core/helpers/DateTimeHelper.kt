package com.brighttorchstudio.schedist.core.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
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

        fun localDateToMillis(localDate: LocalDate): Long {
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        }

        fun millisToLocalDate(millis: Long): LocalDate {
            return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
        }

        fun localTimeToFormattedString(localTime: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localTime.format(formatter)
        }

    }
}