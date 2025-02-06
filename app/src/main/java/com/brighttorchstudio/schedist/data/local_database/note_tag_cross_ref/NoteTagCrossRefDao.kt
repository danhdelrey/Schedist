package com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagCrossRefDao {
    @Transaction
    @Query("SELECT * FROM NoteEntity ORDER BY dateCreated DESC")
    fun getNotesWithTags(): Flow<List<NoteWithTagsRef>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteTagCrossRef(crossRef: NoteTagCrossRefEntity)

    //xóa tag khỏi ghi chú cụ thể
    @Query("DELETE FROM NoteTagCrossRefEntity WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun deleteNoteTagCrossRef(noteId: String, tagId: String)

    //xóa note khỏi bảng --> gọi NoteDao để xóa luôn
    @Query("DELETE FROM NoteTagCrossRefEntity WHERE noteId = :noteId")
    suspend fun deleteNoteTagCrossRefsByNoteId(noteId: String)

    //thêm một tag vào một ghi chú cụ thể
    @Transaction
    suspend fun addTagToNote(noteId: String, tagId: String) {
        insertNoteTagCrossRef(NoteTagCrossRefEntity(noteId, tagId))
    }
}