package com.liangsan.keyloler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ForumEntity(
    @PrimaryKey
    val fid: Int,
    val name: String,
    val threads: Int,
    val posts: Int,
    val todayPosts: Int,
    val description: String?,
    val icon: String?,
    val subList: List<ForumEntity>?
)