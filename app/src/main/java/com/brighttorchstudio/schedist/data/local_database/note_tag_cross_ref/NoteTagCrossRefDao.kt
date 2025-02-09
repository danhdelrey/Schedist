package com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteTagCrossRefDao {
    //Annotation này đảm bảo toàn bộ thao tác được thực hiện trong một transaction.
    // Điều này quan trọng để đảm bảo tính nhất quán của dữ liệu, đặc biệt khi truy vấn dữ liệu từ nhiều bảng.
    @Transaction
    //Mặc dù truy vấn chỉ SELECT từ NoteEntity,
    // Room sẽ tự động thực hiện các join cần thiết với NoteTagCrossRefEntity và TagEntity
    // để trả về List<NoteWithTagsRef>, nhờ vào định nghĩa của NoteWithTagsRef và @Relation trong đó
    @Query("SELECT * FROM NoteEntity ORDER BY dateCreated DESC")
    fun getNotesWithTags(): Flow<List<NoteWithTagsRef>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteTagCrossRef(crossRef: NoteTagCrossRefEntity)

    //Hàm này xóa một mục từ NoteTagCrossRefEntity dựa trên noteId và tagId.
    //Nói cách khác, nó xóa mối liên kết giữa một ghi chú cụ thể và một tag cụ thể.
    @Query("DELETE FROM NoteTagCrossRefEntity WHERE noteId = :noteId AND tagId = :tagId")
    suspend fun deleteNoteTagCrossRef(noteId: String, tagId: String)

    //Hàm này xóa tất cả các mục từ NoteTagCrossRefEntity có noteId khớp với noteId được cung cấp.
    //Nói cách khác, nó xóa tất cả các tag "liên kết" với một ghi chú cụ thể.
    //Lưu ý rằng hàm này chỉ xóa các mục trong bảng trung gian, chứ không xóa bản thân ghi chú.
    //Bạn cần gọi một hàm khác trong NoteDao để xóa ghi chú khỏi bảng NoteEntity.
    @Query("DELETE FROM NoteTagCrossRefEntity WHERE noteId = :noteId")
    suspend fun deleteNoteTagCrossRefsByNoteId(noteId: String)

    //liên kết 1 tag với 1 note
    @Transaction
    suspend fun addTagToNote(noteId: String, tagId: String) {
        insertNoteTagCrossRef(NoteTagCrossRefEntity(noteId, tagId))
    }
}