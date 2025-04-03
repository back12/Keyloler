package com.liangsan.keyloler.presentation.my_thread

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.liangsan.keyloler.domain.repository.ThreadsRepository

class MyThreadViewModel(
    threadsRepository: ThreadsRepository
) : ViewModel() {

    val myThread = threadsRepository.getMyThread().cachedIn(viewModelScope)
}