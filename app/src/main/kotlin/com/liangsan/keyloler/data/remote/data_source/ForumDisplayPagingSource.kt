package com.liangsan.keyloler.data.remote.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.mapToResult
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.domain.utils.data
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class ForumDisplayPagingSource(
    private val fid: Int,
    private val service: KeylolerService,
) : PagingSource<Int, Thread>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Thread> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = service.getForumDisplay(fid, nextPageNumber)
            val threadList = response.mapToResult().data?.threadList ?: emptyList()
            return LoadResult.Page(
                data = threadList,
                prevKey = null,
                nextKey = (nextPageNumber + 1).takeIf { threadList.isNotEmpty() }
            )
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Thread>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}