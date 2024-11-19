package com.liangsan.keyloler.data.local.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity

data class ForumsWithCategory(
    @Embedded
    val category: ForumCategoryEntity,
    @Relation(
        parentColumn = "fcid",
        entityColumn = "fid",
        associateBy = Junction(ForumCategoryCrossRef::class)
    )
    val forumList: List<ForumEntity>
)
