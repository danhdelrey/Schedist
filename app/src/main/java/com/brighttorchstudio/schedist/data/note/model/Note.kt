package com.brighttorchstudio.schedist.data.note.model

import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.tag.model.Tag
import java.time.LocalDateTime

data class Note(
    val id: String,
    val title: String,
    val description: String,
    var tags: List<Tag>,
    val dateTime: LocalDateTime,
    val dateCreated: LocalDateTime,
) {
    fun toEntity(): NoteEntity {
        return NoteEntity(
            noteId = id,
            title = title,
            description = description,
            dateTime = dateTime,
            dateCreated = dateCreated
        )
    }

    companion object {
        fun fromEntity(noteEntity: NoteEntity): Note {
            return Note(
                id = noteEntity.noteId,
                title = noteEntity.title,
                description = noteEntity.description,
                tags = noteEntity.tags,
                dateTime = noteEntity.dateTime,
                dateCreated = noteEntity.dateCreated
            )
        }
    }
}

