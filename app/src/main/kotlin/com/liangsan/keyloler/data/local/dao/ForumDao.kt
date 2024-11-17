package com.liangsan.keyloler.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.relation.ForumsWithCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ForumDao {

    @Transaction
    @Query("SELECT * FROM forumcategoryentity")
    fun getForumsWithCategory(): Flow<List<ForumsWithCategory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForumCategory(forumCategory: List<ForumCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForum(forumEntity: List<ForumEntity>)

    @Query("DELETE FROM forumcategoryentity")
    suspend fun clearAllForumCategory()

    @Query("DELETE FROM forumentity")
    suspend fun clearAllForum()
}