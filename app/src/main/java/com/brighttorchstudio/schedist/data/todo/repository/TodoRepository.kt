package com.brighttorchstudio.schedist.data.todo.repository

import com.brighttorchstudio.schedist.data.todo.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<Todo>>
    suspend fun addTodo(todo: Todo)
    suspend fun addTodo(todo: List<Todo>)
    suspend fun deleteTodo(todo: Todo)
    suspend fun deleteTodo(todo: List<Todo>)
    suspend fun updateTodo(todo: Todo)
    suspend fun updateTodo(todo: List<Todo>)
    suspend fun deleteAllTodos()

}