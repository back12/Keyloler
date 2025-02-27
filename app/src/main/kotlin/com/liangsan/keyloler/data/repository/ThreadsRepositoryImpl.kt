package com.liangsan.keyloler.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.mapper.toDomain
import com.liangsan.keyloler.data.mapper.toThreadHistoryEntity
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.data_source.ForumDisplayPagingSource
import com.liangsan.keyloler.data.remote.dto.mapToResult
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ThreadsRepositoryImpl(
    db: KeylolerDatabase,
    private val service: KeylolerService
) : ThreadsRepository {

    private val historyDao = db.threadHistoryDao()

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

    override suspend fun insertHistory(thread: Thread) {
        historyDao.insertNewThread(thread.toThreadHistoryEntity())
    }

    override fun getThreadHistoryOverview(): Flow<List<Thread>> {
        return historyDao.getHistoryOverview().map { list -> list.map { it.toDomain() } }
    }

    override fun getThreadHistory(query: String): Flow<PagingData<Thread>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 50)
        ) {
            historyDao.pagingSource(query)
        }
        return pager.flow.map { it.map { it.toDomain() } }
    }

    override suspend fun clearHistory() {
        historyDao.clearAll()
    }
}