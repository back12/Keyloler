package com.liangsan.keyloler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewThreadEntity(
    @PrimaryKey
    val tid: String,
    val readPerm: String,
    val author: String,
    val authorId: String,
    val subject: String,
    val dateline: String,
    val lastPost: String,
    val lastPoster: String,
    val views: String,
    val replies: String,
    val digest: String,
    val attachment: String,
    val dbDateline: Long,
    val dbLastPost: String,
)
