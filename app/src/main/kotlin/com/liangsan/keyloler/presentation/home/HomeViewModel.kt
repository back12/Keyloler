package com.liangsan.keyloler.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.IndexRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val indexRepository: IndexRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.onStart {
        _state.update {
            indexRepository.getIndexContent().let { index ->
                it.copy(
                    index = Result.Success(index),
                    currentTab = index.threadsList.keys.firstOrNull()
                )
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), HomeState())

    fun selectTab(tab: String) {
        _state.update {
            it.copy(currentTab = tab)
        }
    }
}