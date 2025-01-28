package com.brighttorchstudio.schedist.data.local_database

import android.content.Context
import androidx.room.Room
import com.brighttorchstudio.schedist.data.local_database.note.NoteDao
import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

//Nơi khởi tạo database bằng Hilt
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTodoDao(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }

    fun provideNoteDao(todoDatabase: TodoDatabase): NoteDao {
        return todoDatabase.noteDao()
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }

}