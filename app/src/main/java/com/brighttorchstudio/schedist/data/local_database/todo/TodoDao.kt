package com.brighttorchstudio.schedist.data.local_database.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

//Định nghĩa các phương thức trừu tượng tương tác với database
//Room sẽ tự generate code dựa trên các interface này
@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoEntity ORDER BY dateTime")
    fun getTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM TodoEntity WHERE id = :id")
    fun getTodoById(id: String): TodoEntity

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

}