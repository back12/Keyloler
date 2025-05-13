package com.liangsan.keyloler.presentation.home

import pro.respawn.flowmvi.api.MVIIntent

sealed interface HomeIntent : MVIIntent {
    data class SelectTab(val tab: String) : HomeIntent
}