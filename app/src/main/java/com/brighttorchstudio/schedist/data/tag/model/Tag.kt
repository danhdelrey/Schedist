package com.brighttorchstudio.schedist.data.tag.model

import com.brighttorchstudio.schedist.data.local_database.tag.TagEntity

data class Tag(
    val id: String,
    val name: String,
    val color: Long
){
    fun toEntity() : TagEntity {
        return TagEntity(
            id = id,
            name = name,
            color = color
        )
    }

    companion object{
        fun fromEntity(tagEntity : TagEntity): Tag{
            return Tag(
                id = tagEntity.id,
                name = tagEntity.name,
                color = tagEntity.color
            )
        }
    }
}
