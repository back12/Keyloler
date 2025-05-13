package com.liangsan.keyloler.presentation.thread

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.liangsan.keyloler.domain.model.Post
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.utils.data
import com.liangsan.keyloler.presentation.thread.navigation.ViewThread
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import pro.respawn.flowmvi.api.ActionReceiver
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.init
import java.util.concurrent.atomic.AtomicBoolean

class ThreadViewModel(
    savedStateHandle: SavedStateHandle,
    threadsRepository: ThreadsRepository
) : ViewModel(), Container<ThreadState, Nothing, ThreadAction> {

    private val route = savedStateHandle.toRoute<ViewThread>()
    private val read = AtomicBoolean(false)

    override val store by lazyStore(
        initial = ThreadState.Loading,
        scope = viewModelScope
    ) {
        init {
            threadsRepository.viewThread(route.tid)
                .firstOrNull { it.isSuccessful() }?.let {
                    it.data?.let {
                        updateState { ThreadState.DisplayThread(it) }
                        if (!read.get()) {
                            launch {
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
                }
        }
    }

    private suspend fun ActionReceiver<ThreadAction>.jumpToPost(
        postList: List<Post>,
        pid: String
    ) {
        val index = postList.indexOfFirst { it.pid == pid }.takeIf { it != -1 } ?: return
        action(ThreadAction.JumpToPost(index))
    }

}