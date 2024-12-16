package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ThreadsRepository {

    fun getNewThreads(
        fids: String,
        limit: Int,
        start: Int
    ): Flow<Result<List<Thread>>>
}