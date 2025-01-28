package com.brighttorchstudio.schedist.data.note.repository

import com.brighttorchstudio.schedist.data.note.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes() : Flow<List<Note>>
    suspend fun addNote(note: Note)
    suspend fun addNotes(notes: List<Note>)
    suspend fun deleteNote(note: Note)
    suspend fun deleteNotes(notes: List<Note>)
    suspend fun deleteAllNotes()
    suspend fun updateNote(note: Note)
}