package com.brighttorchstudio.schedist.data.local_database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TagEntity(
    @PrimaryKey val tagId: String,
    val name: String,
    val color: Long
)