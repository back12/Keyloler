package com.liangsan.keyloler.presentation.search_index.index

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import com.liangsan.keyloler.domain.utils.onError
import com.liangsan.keyloler.domain.utils.onSuccess
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.flow.onEach
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.whileSubscribed

class IndexViewModel(
    repository: ForumCategoryRepository
) : ViewModel(), Container<IndexState, Nothing, Nothing> {

    override val store by lazyStore(
        initial = IndexState(),
        scope = viewModelScope
    ) {
        whileSubscribed {
            repository.fetchForumIndex().onEach {
                it.onError {
                    SnackbarController.showSnackbar(it.toString())
                }.onSuccess {
                    updateState {
                        IndexState(forumCategoryList = it)
                    }
                }
            }.consume()
        }
    }
}