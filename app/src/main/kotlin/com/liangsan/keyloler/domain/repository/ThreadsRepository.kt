package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ThreadsRepository {

    fun viewThread(tid: String, page: Int = 1, cp: String = "all"): Flow<Result<ThreadContent>>

    fun getNewThreads(
        fids: String,
        limit: Int,
        start: Int
    ): Flow<Result<List<Thread>>>
}