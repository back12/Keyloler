package com.liangsan.keyloler.presentation.thread_history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreadHistoryViewModel(
    threadsRepository: ThreadsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ThreadHistoryState())

    @OptIn(ExperimentalCoroutinesApi::class)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            state.distinctUntilChangedBy { it.searchInput }.collectLatest { state ->
                _state.update { it.copy(historyList = threadsRepository.getThreadHistory(state.searchInput)) }
            }
        }
    }

    fun changeSearchInput(input: String) {
        _state.update { it.copy(searchInput = input) }
    }
}