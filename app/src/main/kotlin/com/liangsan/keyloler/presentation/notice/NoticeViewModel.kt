package com.liangsan.keyloler.presentation.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.liangsan.keyloler.domain.repository.UserRepository

class NoticeViewModel(
    userRepository: UserRepository
) : ViewModel() {

    val notice = userRepository.getMyNoteList().cachedIn(viewModelScope)
}