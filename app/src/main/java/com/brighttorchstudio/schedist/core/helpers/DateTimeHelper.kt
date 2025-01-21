package com.brighttorchstudio.schedist.core.helpers

import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

//Lớp này chứa các phương thức tĩnh liên quan đến ngày giờ
class DateTimeHelper {

    //Khai báo các phương thức tĩnh trong companion object
    companion object {

        //format một LocalDateTime thành dạng HH:mm dd/MM/yyyy
        fun formatLocalDateTime(dateTime: LocalDateTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")
            return dateTime.format(formatter)
        }

        //Kiểm tra xem ngày đó có tới hạn chưa
        fun isDue(dateTime: LocalDateTime): Boolean {
            return dateTime.isBefore(LocalDateTime.now())
        }

        //chuyển LocalDate thành một số Long 
        fun localDateToMillis(localDate: LocalDate): Long {
            return localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli()
        }

        //chuyển một số Long thành LocalDate
        fun millisToLocalDate(millis: Long): LocalDate {
            return Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
        }

        //format một LocalTime thành dạng HH:mm
        fun localTimeToFormattedString(localTime: LocalTime): String {
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return localTime.format(formatter)
        }

        fun calculateSecondsDifference(inputDateTime: LocalDateTime): Long {
            val now = LocalDateTime.now()
            val duration = Duration.between(inputDateTime, now)
            return duration.seconds
        }


    }
}