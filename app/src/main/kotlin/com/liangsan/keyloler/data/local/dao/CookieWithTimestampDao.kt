package com.liangsan.keyloler.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import kotlinx.coroutines.flow.Flow

@Dao
interface CookieWithTimestampDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCookieWithTimestamp(cookieWithTimestamp: CookieWithTimestamp)

    @Query("SELECT * FROM cookiewithtimestamp")
    suspend fun getAllCookieWithTimeStamp(): List<CookieWithTimestamp>

    @Query("SELECT * FROM cookiewithtimestamp")
    fun getAllCookieWithTimeStampFlow(): Flow<List<CookieWithTimestamp>>

    @Delete
    suspend fun deleteCookieWithTimeStamp(cookieWithTimestamp: CookieWithTimestamp)
}