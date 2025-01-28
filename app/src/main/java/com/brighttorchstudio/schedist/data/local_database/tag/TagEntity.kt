package com.brighttorchstudio.schedist.data.local_database.tag

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class TagEntity (
    @PrimaryKey val id: String,

)