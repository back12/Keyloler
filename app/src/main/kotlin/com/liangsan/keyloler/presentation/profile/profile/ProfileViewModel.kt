package com.liangsan.keyloler.presentation.profile.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository,
    private val profileRepository: ProfileRepository,
    private val threadsRepository: ThreadsRepository
) : ViewModel() {

    companion object {
        const val NICKNAME = "user_nickname"
        const val AVATAR = "user_avatar"
    }

    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    init {
        observeLoginState()
        observeUserState()
        observeThreadHistory()
    }

    private fun observeThreadHistory() {
        viewModelScope.launch {
            threadsRepository.getThreadHistoryOverview().collectLatest { history ->
                _state.update {
                    it.copy(threadHistoryOverView = history)
                }
            }
        }
    }

    private fun observeLoginState() {
        viewModelScope.launch {
            userRepository.getUserData()
                .map { it.uid }
                .distinctUntilChanged()
                .collectLatest { uid ->
                    if (uid.isNotBlank()) {
                        _state.update { it.copy(uid = uid) }
                        val nickname = profileRepository.getUserNickname(uid)
                        val avatar = profileRepository.getUserAvatarUrl(uid)
                        savedStateHandle[NICKNAME] = nickname
                        savedStateHandle[AVATAR] = avatar
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeUserState() {
        viewModelScope.launch {
            state.filter { it.uid != null }
                .flatMapLatest {
                    combine(
                        savedStateHandle.getStateFlow(NICKNAME, ""),
                        savedStateHandle.getStateFlow(AVATAR, "")
                    ) { nickname, avatar ->
                        _state.update {
                            it.copy(
                                userNickname = nickname,
                                userAvatar = avatar
                            )
                        }
                    }
                }.collect()
        }
    }
}