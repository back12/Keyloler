package com.liangsan.keyloler.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.KeylolRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: KeylolRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            repository.getForumIndex().also { println(it) }
        }
    }
}