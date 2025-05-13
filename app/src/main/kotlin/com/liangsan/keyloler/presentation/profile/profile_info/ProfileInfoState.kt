package com.liangsan.keyloler.presentation.profile.profile_info

import com.liangsan.keyloler.domain.model.ProfileInfo
import pro.respawn.flowmvi.api.MVIState

data class ProfileInfoState(val profile: ProfileInfo? = null) : MVIState