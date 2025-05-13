package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface ForumCategoryRepository {

    suspend fun fetchForumIndex(
        refresh: Boolean = false
    ): Flow<Result<ForumWithCategoryList>>
}