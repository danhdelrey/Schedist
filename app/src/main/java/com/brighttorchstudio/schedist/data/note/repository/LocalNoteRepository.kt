package com.brighttorchstudio.schedist.data.note.repository

import com.brighttorchstudio.schedist.data.local_database.note.NoteDao
import com.brighttorchstudio.schedist.data.note.model.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalNoteRepository @Inject constructor(
    private val noteDao: NoteDao
) : NoteRepository{
    override fun getNotes(): Flow<List<Note>> {
        return noteDao.getNotes().map { notes -> notes.map{
           Note.fromEntity(it)
        } }
    }

    override suspend fun addNote(note: Note) {
        noteDao.addNote(note.toEntity())
    }

    override suspend fun addNotes(notes: List<Note>) {
        notes.map{
            noteDao.addNote(it.toEntity())
        }
    }

    override suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note.toEntity())
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        notes.map{
            noteDao.deleteNote(it.toEntity())
        }
    }

    override suspend fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    override suspend fun updateNote(note: Note) {
        noteDao.updateNote(note.toEntity())
    }
}