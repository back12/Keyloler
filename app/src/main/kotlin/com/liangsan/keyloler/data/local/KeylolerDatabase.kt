package com.liangsan.keyloler.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.liangsan.keyloler.data.local.converter.ForumEntityListConverter
import com.liangsan.keyloler.data.local.dao.ForumDao
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.relation.ForumCategoryCrossRef

@Database(
    entities = [
        ForumEntity::class,
        ForumCategoryEntity::class,
        ForumCategoryCrossRef::class,
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    ForumEntityListConverter::class,
)
abstract class KeylolerDatabase : RoomDatabase() {

    abstract fun forumDao(): ForumDao
}