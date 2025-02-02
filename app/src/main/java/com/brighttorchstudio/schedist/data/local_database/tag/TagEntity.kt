package com.brighttorchstudio.schedist.data.local_database.tag

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class TagEntity (
    @PrimaryKey val id: String,
    val name : String,
    val color : Long
)