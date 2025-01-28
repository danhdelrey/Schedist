package com.brighttorchstudio.schedist.data.local_database.note

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import java.time.LocalDateTime

@Entity
data class NoteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val tags: List<TagEntity>,
    val dateTime: LocalDateTime,
)