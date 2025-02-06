package com.brighttorchstudio.schedist.data.note_with_tags.repository

import com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref.NoteTagCrossRefDao
import com.brighttorchstudio.schedist.data.note.model.Note
import com.brighttorchstudio.schedist.data.note_with_tags.model.NoteWithTags
import com.brighttorchstudio.schedist.data.tag.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNoteWithTagsRepository @Inject constructor(
    private val noteTagCrossRefDao: NoteTagCrossRefDao
) : NoteWithTagsRepository {
    override suspend fun getNotesWithTags(): Flow<List<NoteWithTags>> {
        return noteTagCrossRefDao.getNotesWithTags().map { notesWithTags ->
            notesWithTags.map {
                NoteWithTags(
                    note = Note.fromEntity(it.note),
                    tags = it.tags.map {
                        Tag.fromEntity(it)
                    }
                )
            }
        }
    }

    override suspend fun addTagToNote(noteId: String, tagId: String) {
        noteTagCrossRefDao.addTagToNote(noteId, tagId)
    }

    override suspend fun deleteNoteTagCrossRef(noteId: String, tagId: String) {
        noteTagCrossRefDao.deleteNoteTagCrossRef(noteId, tagId)
    }

}