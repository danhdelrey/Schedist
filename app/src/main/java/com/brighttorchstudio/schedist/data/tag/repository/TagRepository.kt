package com.brighttorchstudio.schedist.data.tag.repository

import com.brighttorchstudio.schedist.data.tag.model.Tag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    fun getTags(): Flow<List<Tag>>
    suspend fun getTagById(tagId: String): Tag
    suspend fun addTag(tag: Tag)
    suspend fun getTagsByName (
        tagName :String
    ):List<Tag>

}