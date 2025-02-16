package com.brighttorchstudio.schedist.data.local_database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.brighttorchstudio.schedist.core.common.TagColor

@Entity
data class TagEntity(
    @PrimaryKey val tagId: String,
    val name: String,
    val color: TagColor
)