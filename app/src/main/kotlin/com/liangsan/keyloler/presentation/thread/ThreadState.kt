package com.liangsan.keyloler.presentation.thread

import com.liangsan.keyloler.domain.model.ThreadContent
import pro.respawn.flowmvi.api.MVIState

sealed interface ThreadState : MVIState {
    data object Loading : ThreadState
    data class DisplayThread(val content: ThreadContent) : ThreadState
}