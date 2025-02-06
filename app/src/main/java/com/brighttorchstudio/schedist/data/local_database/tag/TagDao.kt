package com.brighttorchstudio.schedist.data.local_database.tag

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM TagEntity ORDER BY name")
    fun getTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM TagEntity WHERE tagId=:tagId ORDER BY name")
    suspend fun getTagById(tagId: String): TagEntity

    @Insert
    suspend fun addTag(tag: TagEntity)
}