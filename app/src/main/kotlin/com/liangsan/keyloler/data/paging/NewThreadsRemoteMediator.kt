package com.liangsan.keyloler.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.liangsan.keyloler.data.local.entity.NewThreadEntity
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import kotlinx.io.IOException

@OptIn(ExperimentalPagingApi::class)
class NewThreadsRemoteMediator(
    private val threadsRepository: ThreadsRepository
) : RemoteMediator<Int, NewThreadEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, NewThreadEntity>
    ): MediatorResult {
        return try {
            MediatorResult.Error(RuntimeException())
        } catch (e: IOException) {
            MediatorResult.Error(e)
        }
    }

}