package com.liangsan.keyloler.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.IndexRepository
import com.liangsan.keyloler.domain.utils.Result
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.init
import pro.respawn.flowmvi.plugins.reduce

class HomeViewModel(
    private val indexRepository: IndexRepository
) : ViewModel(), Container<HomeState, HomeIntent, Nothing> {

    override val store by lazyStore(
        initial = HomeState(),
        scope = viewModelScope
    ) {
        init {
            indexRepository.getIndexContent().let { index ->
                updateState {
                    copy(
                        index = Result.Success(index),
                        currentTab = index.threadsList.keys.firstOrNull()
                    )
                }
            }
        }

        reduce {
            when (it) {
                is HomeIntent.SelectTab -> updateState { copy(currentTab = it.tab) }
            }
        }
    }
}