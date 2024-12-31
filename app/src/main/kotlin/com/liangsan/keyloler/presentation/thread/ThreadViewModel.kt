package com.liangsan.keyloler.presentation.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class ThreadViewModel(
    tid: String,
    threadsRepository: ThreadsRepository
) : ViewModel() {

    val state: StateFlow<Result<ThreadContent>> = threadsRepository.viewThread(tid)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)


}