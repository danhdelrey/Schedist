package com.brighttorchstudio.schedist.data.note.model

import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import java.time.LocalDateTime

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val tags: List<TagEntity>,
    val dateTime: LocalDateTime,
){
    fun toEntity() : NoteEntity{
        return NoteEntity(
            id = id,
            title = title,
            description = description,
            tags = tags,
            dateTime = dateTime,
        )
    }

    companion object{
        fun fromEntity(noteEntity: NoteEntity) : Note{
            return Note(
                id = noteEntity.id,
                title = noteEntity.title,
                description = noteEntity.description,
                tags = noteEntity.tags,
                dateTime = noteEntity.dateTime
                )
        }
    }
}

