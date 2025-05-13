package com.liangsan.keyloler.presentation.profile.profile_info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.utils.onError
import com.liangsan.keyloler.domain.utils.onSuccess
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.flow.onEach
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.whileSubscribed

class ProfileInfoViewModel(
    uid: String,
    profileRepository: ProfileRepository
) : ViewModel(), Container<ProfileInfoState, Nothing, Nothing> {

    override val store by lazyStore(
        initial = ProfileInfoState(),
        scope = viewModelScope
    ) {
        whileSubscribed {
            profileRepository.getProfileInfo(uid)
                .onEach {
                    it.onError {
                        SnackbarController.showSnackbar(it.toString())
                    }.onSuccess {
                        updateState {
                            ProfileInfoState(it)
                        }
                    }
                }.consume()
        }
    }
}