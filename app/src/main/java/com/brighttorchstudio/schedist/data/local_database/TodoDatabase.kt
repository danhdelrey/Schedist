package com.brighttorchstudio.schedist.data.local_database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.brighttorchstudio.schedist.data.local_database.note.NoteDao
import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref.NoteTagCrossRefDao
import com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref.NoteTagCrossRefEntity
import com.brighttorchstudio.schedist.data.local_database.tag.TagDao
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import com.brighttorchstudio.schedist.data.local_database.todo.TodoDao
import com.brighttorchstudio.schedist.data.local_database.todo.TodoEntity

//Khai báo các entities trong đây
@Database(
    entities = [
        TodoEntity::class,
        NoteEntity::class,
        TagEntity::class,
        NoteTagCrossRefEntity::class
    ],
    version = 2
)
@TypeConverters(Converter::class) //Cho Room biết sử dụng lớp Converter để chuyển đổi các kiểu dữ liệu mà nó không hỗ trợ
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
    abstract fun noteTagDao(): NoteTagCrossRefDao
}