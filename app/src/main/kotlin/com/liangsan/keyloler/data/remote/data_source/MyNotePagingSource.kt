package com.liangsan.keyloler.data.remote.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.mapToResult
import com.liangsan.keyloler.domain.model.Notice
import com.liangsan.keyloler.domain.utils.data
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

class MyNotePagingSource(
    private val service: KeylolerService,
) : PagingSource<Int, Notice>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Notice> {
        try {
            val nextPageNumber = params.key ?: 1
            val response = service.getMyNoteList(nextPageNumber)
            val noticeList = response.mapToResult().data?.list ?: emptyList()
            return LoadResult.Page(
                data = noticeList,
                prevKey = null,
                nextKey = (nextPageNumber + 1).takeIf { noticeList.isNotEmpty() }
            )
        } catch (e: Exception) {
            coroutineContext.ensureActive()
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Notice>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}