package com.liangsan.keyloler.presentation.main

import pro.respawn.flowmvi.api.MVIState

data class AppState(
    val uid: String? = null,
    val userNickname: String = "",
    val userAvatar: String = "",
) : MVIState
