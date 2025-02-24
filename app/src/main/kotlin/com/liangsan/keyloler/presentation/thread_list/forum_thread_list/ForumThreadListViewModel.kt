package com.liangsan.keyloler.presentation.thread_list.forum_thread_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.liangsan.keyloler.domain.repository.ThreadsRepository

class ForumThreadListViewModel(
    fid: Int,
    threadsRepository: ThreadsRepository
) : ViewModel() {

    val threadList = threadsRepository.getThreadsByForumId(fid).cachedIn(viewModelScope)
}