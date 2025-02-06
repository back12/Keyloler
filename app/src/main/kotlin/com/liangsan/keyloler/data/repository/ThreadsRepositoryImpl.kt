package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.mapToResult
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ThreadsRepositoryImpl(
    private val service: KeylolerService
) : ThreadsRepository {

    override fun viewThread(tid: String, page: Int, cp: String): Flow<Result<ThreadContent>> =
        flow {
            emit(Result.Loading)

            val result = service.viewThread(tid = tid, page = page, cp = cp).mapToResult()
            emit(result)
        }

    override fun getNewThreads(
        fids: String,
        limit: Int,
        start: Int
    ): Flow<Result<List<Thread>>> = flow {

    }
}