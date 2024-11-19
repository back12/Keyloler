package com.liangsan.keyloler.data.local.relation

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["fcid", "fid"])
data class ForumCategoryCrossRef(
    val fcid: Int,
    @ColumnInfo(index = true)
    val fid: Int
)