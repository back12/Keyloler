package com.liangsan.keyloler.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liangsan.keyloler.data.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearchHistory(searchHistoryEntity: SearchHistoryEntity)

    @Query("SELECT * FROM searchhistoryentity ORDER BY createTime DESC LIMIT :limit")
    fun getNewestSearchHistory(limit: Int): Flow<List<SearchHistoryEntity>>

    @Delete
    suspend fun deleteSearchHistory(searchHistoryEntity: SearchHistoryEntity)

    @Query("DELETE FROM searchhistoryentity")
    suspend fun clearSearchHistory()
}