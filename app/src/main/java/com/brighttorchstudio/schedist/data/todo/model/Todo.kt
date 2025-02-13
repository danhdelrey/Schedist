package com.brighttorchstudio.schedist.data.todo.model

import com.brighttorchstudio.schedist.core.common.ImportanceLevel
import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity
import java.time.LocalDateTime

//Mặc dù thuộc tính giống TodoEntity nhưng TodoEntity (entity) chỉ được dùng trong database, còn để sử dụng trong giao diện thì phải dùng Todo (model)
data class Todo(
    val id: String,
    val title: String,
    val description: String,
    val importanceLevel: ImportanceLevel,
    val dateTime: LocalDateTime,
    val reminderEnabled: Boolean,
    var subtasks : List<Subtask>
) {

    //phương thức tạo ra TodoEntity từ một Todo
    fun toEntity(): TodoEntity {
        return TodoEntity(
            id = id,
            title = title,
            description = description,
            importanceLevel = importanceLevel,
            dateTime = dateTime,
            reminderEnabled = reminderEnabled,
            subtasks = subtasks
        )
    }

    //Các phương thức tĩnh
    companion object {
        //Tạo ra một Todo từ một TodoEntity
        fun fromEntity(todoEntity: TodoEntity): Todo {
            return Todo(
                id = todoEntity.id,
                title = todoEntity.title,
                description = todoEntity.description,
                importanceLevel = todoEntity.importanceLevel,
                dateTime = todoEntity.dateTime,
                reminderEnabled = todoEntity.reminderEnabled,
                subtasks = todoEntity.subtasks
            )
        }
    }
}