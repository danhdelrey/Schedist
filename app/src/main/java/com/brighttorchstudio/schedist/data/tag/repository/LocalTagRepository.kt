package com.brighttorchstudio.schedist.data.tag.repository

import com.brighttorchstudio.schedist.data.local_database.tag.TagDao
import com.brighttorchstudio.schedist.data.tag.model.Tag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalTagRepository @Inject constructor(
    private val tagDao: TagDao
) : TagRepository{
    override fun getTags(): Flow<List<Tag>> {
        return tagDao.getTags().map{tags -> tags.map{
            Tag.fromEntity(it)
        }}
    }

    override suspend fun getTagById(tagId: String): Tag {
        return Tag.fromEntity(tagDao.getTagById(tagId))
    }

    override suspend fun addTag(tag: Tag) {
        tagDao.addTag(tag.toEntity())
    }

}