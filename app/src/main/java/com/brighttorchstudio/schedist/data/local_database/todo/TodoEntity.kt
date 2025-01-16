package com.brighttorchstudio.schedist.data.local_database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import java.time.LocalDateTime

@Entity
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val importanceLevel: ImportanceLevel,
    val dateTime: LocalDateTime,
    val reminderEnabled: Boolean,
)



