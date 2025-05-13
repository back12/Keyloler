package com.liangsan.keyloler.presentation.profile.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import kotlinx.coroutines.flow.collectLatest
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.whileSubscribed

class ProfileViewModel(
    private val threadsRepository: ThreadsRepository
) : ViewModel(), Container<ProfileState, Nothing, Nothing> {

    override val store by lazyStore(
        initial = ProfileState(),
        scope = viewModelScope
    ) {
        whileSubscribed {
            threadsRepository.getThreadHistoryOverview().collectLatest { history ->
                updateState {
                    copy(threadHistoryOverView = history)
                }
            }
        }
    }
}