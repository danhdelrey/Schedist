package com.brighttorchstudio.schedist.data.note.model

import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import java.time.LocalDateTime

data class Note(
    val id: String,
    val title: String,
    val description: String,
    var tags: List<String>,
    val dateTime: LocalDateTime,
    val dateCreated : LocalDateTime,
){
    fun toEntity() : NoteEntity{
        return NoteEntity(
            id = id,
            title = title,
            description = description,
            tags = tags,
            dateTime = dateTime,
            dateCreated = dateCreated
        )
    }

    companion object{
        fun fromEntity(noteEntity: NoteEntity) : Note{
            return Note(
                id = noteEntity.id,
                title = noteEntity.title,
                description = noteEntity.description,
                tags = noteEntity.tags,
                dateTime = noteEntity.dateTime,
                dateCreated = noteEntity.dateCreated
                )
        }
    }
}

