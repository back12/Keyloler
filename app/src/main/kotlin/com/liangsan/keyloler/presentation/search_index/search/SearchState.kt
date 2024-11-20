package com.liangsan.keyloler.presentation.search_index.search

import com.liangsan.keyloler.domain.model.SearchHistory
import com.liangsan.keyloler.domain.utils.Result

data class SearchState(
    val searchInput: String = "",
    val searchHistory: List<SearchHistory> = emptyList(),
    val searchResult: Result<Boolean> = Result.Success(false)
)