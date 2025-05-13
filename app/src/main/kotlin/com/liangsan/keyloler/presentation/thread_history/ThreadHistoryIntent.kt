package com.liangsan.keyloler.presentation.thread_history

import pro.respawn.flowmvi.api.MVIIntent

sealed interface ThreadHistoryIntent : MVIIntent {

    data object ShowDialog : ThreadHistoryIntent

    data object DismissDialog : ThreadHistoryIntent

    data object ClearHistory : ThreadHistoryIntent

    data class SetSearchInput(val input: String) : ThreadHistoryIntent
}