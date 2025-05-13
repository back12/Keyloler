package com.liangsan.keyloler.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.repository.UserRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.whileSubscribed

class MainViewModel(
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
) : ViewModel(), Container<AppState, Nothing, Nothing> {

    override val store by lazyStore(
        initial = AppState(),
        scope = viewModelScope
    ) {
        whileSubscribed {
            userRepository.getUserData()
                .map { it.uid }
                .filterNotNull()
                .distinctUntilChanged()
                .collectLatest { uid ->
                    if (uid.isNotBlank()) {
                        val nickname = profileRepository.getUserNickname(uid)
                        val avatar = profileRepository.getUserAvatarUrl(uid)
                        updateState {
                            copy(
                                uid = uid,
                                userNickname = nickname ?: "",
                                userAvatar = avatar
                            )
                        }
                    }
                }
        }
    }
}