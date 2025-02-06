package com.brighttorchstudio.schedist.data.local_database.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class NoteEntity(
    @PrimaryKey val noteId: String,
    val title: String,
    val description: String,
    val dateCreated: LocalDateTime,
    val dateTime: LocalDateTime,
)