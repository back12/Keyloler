package com.liangsan.keyloler.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity

data class ForumsWithCategory(
    @Embedded
    val category: ForumCategoryEntity,
    @Relation(
        parentColumn = "fcid",
        entityColumn = "fid"
    )
    val forumList: List<ForumEntity>
)
