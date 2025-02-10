package com.brighttorchstudio.schedist.data.tag.model

import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity
import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val id: String,
    val name: String,
    val color: Long
) {
    fun toEntity(): TagEntity {
        return TagEntity(
            tagId = id,
            name = name,
            color = color
        )
    }

    companion object {
        fun fromEntity(tagEntity: TagEntity): Tag {
            return Tag(
                id = tagEntity.tagId,
                name = tagEntity.name,
                color = tagEntity.color
            )
        }
    }
}
