package com.liangsan.keyloler.presentation.search_index.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.SearchHistory
import com.liangsan.keyloler.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import timber.log.Timber

class SearchViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    companion object {
        private const val SEARCH_INPUT = "search_input"
    }

    private val searchInputFlow = savedStateHandle.getStateFlow(SEARCH_INPUT, "")

    val state = combine(
        searchInputFlow,
        searchHistoryRepository.getNewestSearchHistory()
    ) { input, history ->
        SearchState(
            searchInput = input,
            searchHistory = history
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), SearchState())

    private var searchJob: Job? = null

    fun searchForThreads() {
        realSearch()
    }

    fun searchForThreadsFromHistory(content: String) {
        realSearch(content)
    }

    private fun realSearch(history: String? = null) {
        viewModelScope.launch outerLaunch@{
            searchJob?.cancel()
            searchJob = launch {
                val content = history ?: state.value.searchInput
                if (content.isBlank()) return@launch
                Timber.d("Search for threads {$content}")
                addNewSearchHistory(
                    SearchHistory(
                        content = content,
                        createTime = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
                //TODO
            }
        }
    }

    private suspend fun addNewSearchHistory(searchHistory: SearchHistory) {
        searchHistoryRepository.addNewSearchHistory(searchHistory)
    }

    fun setSearchInput(input: String) {
        savedStateHandle[SEARCH_INPUT] = input
    }

    fun clearSearchInput() {
        savedStateHandle[SEARCH_INPUT] = ""
    }

    fun deleteSearchHistory(searchHistory: SearchHistory) {
        viewModelScope.launch {
            searchHistoryRepository.deleteSearchHistory(searchHistory)
        }
    }

    fun clearSearchHistory() {
        viewModelScope.launch {
            searchHistoryRepository.clearSearchHistory()
        }
    }
}