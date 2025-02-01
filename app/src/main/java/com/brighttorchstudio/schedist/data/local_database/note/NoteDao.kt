package com.brighttorchstudio.schedist.data.local_database.note

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM NoteEntity ORDER BY dateCreated DESC")
    fun getNotes(): Flow<List<NoteEntity>>

    @Insert
    suspend fun addNote(note: NoteEntity)

    /*@Insert
    suspend fun addNotes(notes: List<NoteEntity>)*/

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    /*@Delete
    suspend fun deleteNotes(notes: List<NoteEntity>)*/

    @Query("DELETE FROM NoteEntity")
    suspend fun deleteAllNotes()

    @Update
    suspend fun updateNote(note: NoteEntity)
}