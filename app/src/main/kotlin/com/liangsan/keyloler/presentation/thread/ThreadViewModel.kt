package com.liangsan.keyloler.presentation.thread

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.liangsan.keyloler.domain.model.Post
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.domain.utils.data
import com.liangsan.keyloler.presentation.thread.navigation.ViewThread
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

class ThreadViewModel(
    savedStateHandle: SavedStateHandle,
    threadsRepository: ThreadsRepository
) : ViewModel() {

    private val route = savedStateHandle.toRoute<ViewThread>()

    private val read = AtomicBoolean(false)

    private val _event = Channel<ThreadScreenEvent>()
    val event = _event.receiveAsFlow()

    val state: StateFlow<Result<ThreadContent>> = threadsRepository.viewThread(route.tid)
        .onEach {
            if (!read.get()) {
                it.data?.let {
                    viewModelScope.launch {
                        // set the thread as read
                        threadsRepository.insertHistory(it.thread)
                        read.set(true)
                        // jump to post if pid is specified
                        route.pid?.let { pid ->
                            jumpToPost(it.postList, pid)
                        }
                    }
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)

    private suspend fun jumpToPost(postList: List<Post>, pid: String) {
        val index = postList.indexOfFirst { it.pid == pid }.takeIf { it != -1 } ?: return
        _event.send(ThreadScreenEvent.JumpToPost(index))
    }

}