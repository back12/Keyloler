package com.liangsan.keyloler.domain.repository

import androidx.paging.PagingData
import com.liangsan.keyloler.domain.model.SteamIframeData
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ThreadsRepository {

    fun viewThread(tid: String, page: Int = 1, cp: String = "all"): Flow<Result<ThreadContent>>

    fun getThreadsByForumId(fid: Int): Flow<PagingData<Thread>>

    suspend fun insertHistory(thread: Thread)

    fun getThreadHistoryOverview(): Flow<List<Thread>>

    fun getThreadHistory(query: String): Flow<PagingData<Thread>>

    suspend fun clearHistory()

    fun getMyThread(): Flow<PagingData<Thread>>

    suspend fun parseSteamIframe(src: String): SteamIframeData?
}