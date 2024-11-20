package com.liangsan.keyloler.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index("content", unique = true)])
data class SearchHistoryEntity(
    val content: String,
    val createTime: Long,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
)
