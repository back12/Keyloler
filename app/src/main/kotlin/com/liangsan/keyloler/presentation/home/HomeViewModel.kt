package com.liangsan.keyloler.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.Index
import com.liangsan.keyloler.domain.repository.IndexRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.init
import pro.respawn.flowmvi.plugins.reduce

class HomeViewModel(
    private val indexRepository: IndexRepository
) : ViewModel(), Container<HomeState, HomeIntent, Nothing> {

    private val _index = MutableStateFlow(Index())

    override val store by lazyStore(
        initial = HomeState(),
        scope = viewModelScope
    ) {
        init {
            indexRepository.getIndexContent().let { index ->
                _index.update { index }
                val firstTab = index.threadsList.keys.firstOrNull()
                updateState {
                    copy(
                        loading = false,
                        currentTab = firstTab,
                        tabs = index.threadsList.keys.toList(),
                        slides = index.slides,
                        currentThreadList = index.threadsList[firstTab] ?: emptyList()
                    )
                }
            }
        }

        reduce {
            when (it) {
                is HomeIntent.SelectTab -> updateState {
                    copy(
                        currentTab = it.tab,
                        currentThreadList = _index.value.threadsList[it.tab] ?: emptyList()
                    )
                }
            }
        }
    }
}