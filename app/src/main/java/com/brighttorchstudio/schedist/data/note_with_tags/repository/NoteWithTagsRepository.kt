package com.brighttorchstudio.schedist.data.note_with_tags.repository

import com.brighttorchstudio.schedist.data.note_with_tags.model.NoteWithTags
import kotlinx.coroutines.flow.Flow

interface NoteWithTagsRepository {
    suspend fun getNotesWithTags(): Flow<List<NoteWithTags>>
    suspend fun addTagToNote(noteId: String, tagId: String)
    suspend fun deleteNoteTagCrossRef(noteId: String, tagId: String)

}