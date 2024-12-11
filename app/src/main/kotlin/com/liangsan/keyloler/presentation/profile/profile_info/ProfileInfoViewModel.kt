package com.liangsan.keyloler.presentation.profile.profile_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn

class ProfileInfoViewModel(
    uid: String,
    profileRepository: ProfileRepository
) : ViewModel() {

    val state = profileRepository.getProfileInfo(uid)
        .onEach {
            if (it is Result.Error) {
                SnackbarController.showSnackbar(it.toString())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), Result.Loading)
}