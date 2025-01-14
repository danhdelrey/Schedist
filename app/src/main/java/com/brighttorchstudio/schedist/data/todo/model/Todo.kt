package com.brighttorchstudio.schedist.data.todo.model

import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity
import java.time.LocalDateTime
import java.util.UUID

data class Todo(
    val id: String,
    val title: String,
    val description: String?,
    val priority: Int,
    val dateTime: LocalDateTime,
    val reminderEnabled: Boolean,
    ){

    fun toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            priority = priority,
            dateTime = dateTime,
            reminderEnabled = reminderEnabled,
        )
    }

    companion object {
        fun fromEntity(todoEntity: TodoEntity): Todo {
            return Todo(
                id = todoEntity.id,
                title = todoEntity.title,
                description = todoEntity.description,
                priority = todoEntity.priority,
                dateTime = todoEntity.dateTime,
                reminderEnabled = todoEntity.reminderEnabled,
            )
        }
    }
}