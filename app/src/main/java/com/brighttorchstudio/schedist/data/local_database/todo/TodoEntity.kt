package com.brighttorchstudio.schedist.data.local_database.todo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighttorchstudio.schedist.data.todo.model.Todo
import java.time.LocalDateTime
import java.util.Date
import java.util.UUID

@Entity
data class TodoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String?,
    val priority: Int,
    val dateTime: LocalDateTime,
    val reminderEnabled: Boolean,
    )



