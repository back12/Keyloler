package com.liangsan.keyloler.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liangsan.keyloler.data.local.converter.CookieConverter
import com.liangsan.keyloler.data.local.converter.ForumEntityListConverter
import com.liangsan.keyloler.data.local.dao.CookieWithTimestampDao
import com.liangsan.keyloler.data.local.dao.ForumDao
import com.liangsan.keyloler.data.local.dao.SearchHistoryDao
import com.liangsan.keyloler.data.local.dao.ThreadHistoryDao
import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.entity.SearchHistoryEntity
import com.liangsan.keyloler.data.local.entity.ThreadHistoryEntity
import com.liangsan.keyloler.data.local.relation.ForumCategoryCrossRef

@Database(
    entities = [
        CookieWithTimestamp::class,
        ForumEntity::class,
        ForumCategoryEntity::class,
        ForumCategoryCrossRef::class,
        SearchHistoryEntity::class,
        ThreadHistoryEntity::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    CookieConverter::class,
    ForumEntityListConverter::class,
)
abstract class KeylolerDatabase : RoomDatabase() {

    abstract fun cookieWithTimestampDao(): CookieWithTimestampDao

    abstract fun forumDao(): ForumDao

    abstract fun searchHistoryDao(): SearchHistoryDao

    abstract fun threadHistoryDao(): ThreadHistoryDao
}