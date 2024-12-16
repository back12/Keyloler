package com.liangsan.keyloler.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liangsan.keyloler.data.local.entity.NewThreadEntity

@Dao
interface NewThreadsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewThreads(threads: List<NewThreadEntity>)

    @Query("SELECT * FROM newthreadentity WHERE dbDateline <= :now")
    fun pagingSource(now: Long): PagingSource<Int, NewThreadEntity>

    @Query("DELETE FROM newthreadentity")
    suspend fun clearAll()
}