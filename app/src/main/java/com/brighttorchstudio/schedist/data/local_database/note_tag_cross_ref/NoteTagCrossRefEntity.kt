package com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRefEntity(
    val noteId: String, // Khóa ngoại tham chiếu đến noteId trong bảng NoteEntity
    val tagId: String // Khóa ngoại tham chiếu đến tagId trong bảng TagEntity
)

// Đây không phải là một entity được lưu trữ trong database
// Nó là một lớp dữ liệu được sử dụng để truy xuất dữ liệu từ nhiều bảng liên quan
// Nó cho phép lấy một NoteEntity cùng với tất cả các TagEntity liên quan
data class NoteWithTagsRef(
    //@Embedded cho phép nhúng một entity khác (ở đây là NoteEntity) vào lớp này
    // Vì vậy, NoteWithTagsRef sẽ chứa tất cả các trường của NoteEntity
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "noteId", //Chỉ định cột trong entity cha (NoteEntity) được sử dụng trong mối quan hệ
        entityColumn = "tagId", //Chỉ định cột trong entity con (TagEntity) được sử dụng trong mối quan hệ
        //Junction cho biết Room sẽ sử dụng bảng NoteTagCrossRefEntity để tìm ra mối quan hệ giữa NoteEntity và TagEntity
        associateBy = Junction(NoteTagCrossRefEntity::class)
    )
    val tags: List<TagEntity>
)
