package com.brighttorchstudio.schedist.data.local_database.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoEntity ORDER BY dateTime")
    fun getTodos(): Flow<List<TodoEntity>>

    @Insert
    suspend fun addTodo(todo: TodoEntity)

    @Insert
    suspend fun addTodo(todoList: List<TodoEntity>)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: List<TodoEntity>)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: List<TodoEntity>)

    @Query("DELETE FROM TodoEntity")
    suspend fun deleteAllTodos()
}