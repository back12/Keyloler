package com.liangsan.keyloler.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liangsan.keyloler.data.local.entity.ThreadHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ThreadHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewThread(thread: ThreadHistoryEntity)

    @Query("SELECT * FROM threadhistoryentity WHERE subject LIKE '%' || :query || '%' ORDER BY createdTime DESC")
    fun pagingSource(query: String): PagingSource<Int, ThreadHistoryEntity>

    @Query("SELECT * FROM threadhistoryentity ORDER BY createdTime DESC LIMIT 5 ")
    fun getHistoryOverview(): Flow<List<ThreadHistoryEntity>>

    @Query("DELETE FROM threadhistoryentity")
    suspend fun clearAll()
}