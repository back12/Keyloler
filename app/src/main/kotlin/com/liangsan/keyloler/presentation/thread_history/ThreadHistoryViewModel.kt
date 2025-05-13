package com.liangsan.keyloler.presentation.thread_history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce
import pro.respawn.flowmvi.plugins.whileSubscribed

class ThreadHistoryViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val threadsRepository: ThreadsRepository
) : ViewModel(), Container<ThreadHistoryState, ThreadHistoryIntent, Nothing> {

    companion object {
        private const val SEARCH_INPUT = "search_input"
    }

    private val searchInputFlow = savedStateHandle.getStateFlow(SEARCH_INPUT, "")

    override val store by lazyStore(
        initial = ThreadHistoryState(),
        scope = viewModelScope
    ) {
        whileSubscribed {
            searchInputFlow.collectLatest { input ->
                updateState {
                    copy(
                        searchInput = input,
                        historyList = threadsRepository.getThreadHistory(input)
                    )
                }
            }
        }

        reduce {
            when (it) {
                ThreadHistoryIntent.ShowDialog -> {
                    updateState {
                        copy(showDialog = true)
                    }
                }

                ThreadHistoryIntent.DismissDialog -> {
                    updateState {
                        copy(showDialog = false)
                    }
                }

                ThreadHistoryIntent.ClearHistory -> {
                    launch {
                        threadsRepository.clearHistory()
                    }
                }

                is ThreadHistoryIntent.SetSearchInput -> {
                    savedStateHandle[SEARCH_INPUT] = it.input
                }
            }
        }
    }
}