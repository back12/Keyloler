package com.liangsan.keyloler.presentation.thread_history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ThreadHistoryViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val threadsRepository: ThreadsRepository
) : ViewModel() {

    companion object {
        private const val SEARCH_INPUT = "search_input"
    }

    private val searchInputFlow = savedStateHandle.getStateFlow(SEARCH_INPUT, "")

    private val _state = MutableStateFlow(ThreadHistoryState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            searchInputFlow.collectLatest { input ->
                _state.update {
                    it.copy(
                        searchInput = input,
                        historyList = threadsRepository.getThreadHistory(input)
                    )
                }
            }
        }
    }

    fun setSearchInput(input: String) {
        savedStateHandle[SEARCH_INPUT] = input
    }

    fun showDialog() {
        _state.update {
            it.copy(showDialog = true)
        }
    }

    fun dismissDialog() {
        _state.update {
            it.copy(showDialog = false)
        }
    }

    fun clearHistory() {
        viewModelScope.launch {
            threadsRepository.clearHistory()
        }
    }
}