package com.liangsan.keyloler.presentation.search_index.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class IndexViewModel(
    repository: ForumCategoryRepository
) : ViewModel() {

    private val loadingState = repository.fetchForumIndex(viewModelScope)

    private val forumCategoryList: Flow<ForumWithCategoryList> = repository.getForumIndex()

    val state = combine(loadingState, forumCategoryList) { loading, list ->
        IndexState(
            forumCategoryList = list,
            loadingState = loading
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), IndexState())
}