package com.brighttorchstudio.schedist.data.local_database

import androidx.room.TypeConverter
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
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

    @TypeConverter
    fun fromImportanceLevel(importanceLevel: ImportanceLevel): String {
        return importanceLevel.name // Lưu trữ tên của enum
    }

    @TypeConverter
    fun toImportanceLevel(value: String): ImportanceLevel {
        return ImportanceLevel.valueOf(value) // Chuyển đổi từ String sang enum
    }
}