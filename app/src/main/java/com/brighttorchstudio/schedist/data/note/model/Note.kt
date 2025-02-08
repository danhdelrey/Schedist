package com.brighttorchstudio.schedist.data.note.model

import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.note_with_tags.model.NoteWithTags
import com.brighttorchstudio.schedist.data.tag.model.Tag
import java.time.LocalDateTime

data class Note(
    val id: String,
    val title: String,
    val description: String,
    val dateTime: LocalDateTime,
    val dateCreated: LocalDateTime,

    var tags: List<Tag> = emptyList(),
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
                dateTime = noteEntity.dateTime,
                dateCreated = noteEntity.dateCreated
            )
        }

        fun fromNoteWithTags(noteWithTags: NoteWithTags): Note {
            return Note(
                id = noteWithTags.note.id,
                title = noteWithTags.note.title,
                description = noteWithTags.note.description,
                dateTime = noteWithTags.note.dateTime,
                dateCreated = noteWithTags.note.dateCreated,
                tags = noteWithTags.tags
            )
        }
    }
}

