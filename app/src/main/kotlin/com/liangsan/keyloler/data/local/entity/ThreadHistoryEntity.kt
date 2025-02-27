package com.liangsan.keyloler.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.liangsan.keyloler.domain.model.Thread

@Entity(primaryKeys = ["tid"])
data class ThreadHistoryEntity(
    @Embedded
    val thread: Thread,
    val createdTime: Long
)