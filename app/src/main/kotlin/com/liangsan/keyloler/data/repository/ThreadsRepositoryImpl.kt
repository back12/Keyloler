package com.liangsan.keyloler.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.data_source.ForumDisplayPagingSource
import com.liangsan.keyloler.data.remote.dto.mapToResult
import com.liangsan.keyloler.domain.model.Thread
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

    override fun getThreadsByForumId(fid: Int): Flow<PagingData<Thread>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = {
                ForumDisplayPagingSource(fid, service)
            }
        )
        return pager.flow
    }

    override fun getNewThreads(
        fids: String,
        limit: Int,
        start: Int
    ): Flow<Result<List<Thread>>> = flow {

    }
}