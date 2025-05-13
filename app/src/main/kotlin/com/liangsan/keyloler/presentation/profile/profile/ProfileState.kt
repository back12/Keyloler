package com.liangsan.keyloler.presentation.profile.profile

import com.liangsan.keyloler.domain.model.Thread
import pro.respawn.flowmvi.api.MVIState

data class ProfileState(
    val threadHistoryOverView: List<Thread> = emptyList()
) : MVIState