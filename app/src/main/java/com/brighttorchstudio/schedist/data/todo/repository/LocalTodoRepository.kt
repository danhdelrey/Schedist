package com.brighttorchstudio.schedist.data.todo.repository


import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity
import com.brighttorchstudio.schedist.data.todo.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTodoRepository @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> = todoDao.getAll().map { todos ->
        todos.map {
            Todo(
                title = it.title
            )
        }
    }

    override suspend fun addTodo(todo: Todo) {
        val newTodo = TodoEntity(
            title = todo.title
        )
        todoDao.insert(newTodo)
    }

    override suspend fun deleteAll() {
        todoDao.deleteAll()
    }

}