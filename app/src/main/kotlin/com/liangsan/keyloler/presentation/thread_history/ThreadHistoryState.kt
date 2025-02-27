package com.liangsan.keyloler.presentation.thread_history

import androidx.paging.PagingData
import com.liangsan.keyloler.domain.model.Thread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ThreadHistoryState(
    val searchInput: String = "",
    val historyList: Flow<PagingData<Thread>> = emptyFlow()
)
