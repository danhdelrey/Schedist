package com.brighttorchstudio.schedist.core.helpers

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
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

        fun localDateToMillis(localDate: LocalDate, zoneId: ZoneId = ZoneId.systemDefault()): Long {
            return localDate.atStartOfDay(zoneId).toInstant().toEpochMilli()
        }

        fun millisToLocalDate(millis: Long, zoneId: ZoneId = ZoneId.systemDefault()): LocalDate {
            return Instant.ofEpochMilli(millis).atZone(zoneId).toLocalDate()
        }

        fun localTimeToFormattedString(localTime: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localTime.format(formatter)
        }

    }
}