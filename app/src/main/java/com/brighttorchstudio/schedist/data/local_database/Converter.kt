package com.brighttorchstudio.schedist.data.local_database

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converter {

    // Chuyển từ LocalDateTime sang Long (milliseconds)
    @TypeConverter
    fun fromDate(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC) * 1000 // Chuyển đổi sang milliseconds
    }

    // Chuyển từ Long (milliseconds) về LocalDateTime
    @TypeConverter
    fun toDate(milliseconds: Long): LocalDateTime {
        val seconds = milliseconds / 1000
        return LocalDateTime.ofEpochSecond(seconds, 0, ZoneOffset.UTC)
    }
}