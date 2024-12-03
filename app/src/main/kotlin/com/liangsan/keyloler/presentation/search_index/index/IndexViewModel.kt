package com.liangsan.keyloler.presentation.search_index.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
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
    }.onEach {
        if (it.loadingState is Result.Error) {
            SnackbarController.showSnackbar(it.loadingState.toString())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), IndexState())
}