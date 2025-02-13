package com.brighttorchstudio.schedist.data.local_database

import android.graphics.Color
import androidx.room.TypeConverter
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import com.brighttorchstudio.schedist.data.tag.model.Tag
import com.brighttorchstudio.schedist.data.todo.model.Subtask
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import java.time.ZoneOffset

//Đây là class giúp Room chuyển đổi các kiểu dữ liệu mà nó không hỗ trợ thành kiểu dữ liệu mà nó hỗ trợ
//Phải đánh dấu các phương thức là TypeConverter
class Converter {

    // Chuyển từ LocalDateTime sang Long (milliseconds)
    //Khi Room gặp kiểu dữ liệu LocalDateTime, nó sẽ vào đây để tìm xem phương thức nào nhận vào LocalDateTime
    @TypeConverter
    fun fromDate(date: LocalDateTime): Long {
        return date.toEpochSecond(ZoneOffset.UTC) * 1000 // Chuyển đổi sang milliseconds
    }

    // Chuyển từ Long (milliseconds) về LocalDateTime
    //Khi truy xuất dữ liệu, Room sẽ vào đây để biến dữ liệu đã được chuyển đổi để lưu trước đó thành LocalDateTime
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

    @TypeConverter
    fun fromTagList(tagList: List<String>) : String = Json.encodeToString(tagList)

    @TypeConverter
    fun toTagList(tagList : String) : List<String> = Json.decodeFromString(tagList)

    @TypeConverter
    fun fromSubtaskList(subtaskList: List<Subtask>) : String = Json.encodeToString(subtaskList)

    @TypeConverter
    fun toSubtaskList(subtaskList : String) : List<Subtask>{
        return Json.decodeFromString(subtaskList)
    }
}