package com.liangsan.keyloler.presentation.thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.domain.utils.data
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class ThreadViewModel(
    tid: String,
    threadsRepository: ThreadsRepository
) : ViewModel() {

    private val read = AtomicBoolean(false)

    val state: StateFlow<Result<ThreadContent>> = threadsRepository.viewThread(tid)
        .onEach {
            if (!read.get()) {
                it.data?.let {
                    viewModelScope.launch {
                        threadsRepository.insertHistory(it.thread)
                        read.set(true)
                    }
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)

}