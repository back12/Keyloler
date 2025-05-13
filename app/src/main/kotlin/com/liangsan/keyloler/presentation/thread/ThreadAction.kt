package com.liangsan.keyloler.presentation.thread

import pro.respawn.flowmvi.api.MVIAction

sealed interface ThreadAction : MVIAction {
    data class JumpToPost(val index: Int) : ThreadAction
}