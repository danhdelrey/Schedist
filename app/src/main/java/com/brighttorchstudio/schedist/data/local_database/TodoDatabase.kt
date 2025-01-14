package com.brighttorchstudio.schedist.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity


@Database(entities = [TodoEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}