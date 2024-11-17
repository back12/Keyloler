package com.liangsan.keyloler.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.liangsan.keyloler.data.local.dao.ForumDao
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity

@Database(
    entities = [
        ForumEntity::class,
        ForumCategoryEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class KeylolerDatabase : RoomDatabase() {

    abstract fun forumDao(): ForumDao
}