package com.liangsan.keyloler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForumCategoryEntity(
    @PrimaryKey
    val fcid: Int,
    val name: String,
)