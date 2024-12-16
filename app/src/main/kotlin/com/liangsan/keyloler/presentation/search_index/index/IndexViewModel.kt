package com.liangsan.keyloler.presentation.search_index.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class IndexViewModel(
    repository: ForumCategoryRepository
) : ViewModel() {

    val state = repository.fetchForumIndex(viewModelScope).map {
        IndexState(forumCategoryList = it)
    }.onEach {
        (it.forumCategoryList as? Result.Error)?.let { error ->
            SnackbarController.showSnackbar(error.toString())
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), IndexState())
}