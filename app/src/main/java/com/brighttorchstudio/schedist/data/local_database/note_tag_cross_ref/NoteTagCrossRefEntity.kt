package com.brighttorchstudio.schedist.data.local_database.note_tag_cross_ref

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation
import com.brighttorchstudio.schedist.data.local_database.note.NoteEntity
import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity

@Entity(primaryKeys = ["noteId", "tagId"])
data class NoteTagCrossRefEntity(
    val noteId: String,
    val tagId: String
)

data class NoteWithTagsRef(
    @Embedded val note: NoteEntity,
    @Relation(
        parentColumn = "noteId",
        entityColumn = "tagId",
        associateBy = Junction(NoteTagCrossRefEntity::class)
    )
    val tags: List<TagEntity>
)
