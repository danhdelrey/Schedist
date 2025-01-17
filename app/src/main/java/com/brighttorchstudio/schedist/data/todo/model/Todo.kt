package com.brighttorchstudio.schedist.data.todo.model

import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity
import java.time.LocalDateTime

data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val importanceLevel: ImportanceLevel,
    val dateTime: LocalDateTime,
    val reminderEnabled: Boolean,
) {

    fun toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            importanceLevel = importanceLevel,
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
                importanceLevel = todoEntity.importanceLevel,
                dateTime = todoEntity.dateTime,
                reminderEnabled = todoEntity.reminderEnabled,
            )
        }
    }
}