package com.brighttorchstudio.schedist.data.todo.repository


import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import com.brighttorchstudio.schedist.data.todo.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTodoRepository @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun getTodos(): Flow<List<Todo>> = todoDao.getTodos().map { todos ->
        todos.map {
            Todo.fromEntity(it)
        }
    }

    override suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo.toEntity())
    }

    override suspend fun addTodo(todo: List<Todo>) {
        todoDao.addTodo(todo.map { it.toEntity() })
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toEntity())
    }

    override suspend fun deleteTodo(todo: List<Todo>) {
        todoDao.deleteTodo(todo.map { it.toEntity() })
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo.toEntity())
    }

    override suspend fun updateTodo(todo: List<Todo>) {
        todoDao.updateTodo(todo.map { it.toEntity() })
    }

    override suspend fun deleteAllTodos() {
        todoDao.deleteAllTodos()
    }


}