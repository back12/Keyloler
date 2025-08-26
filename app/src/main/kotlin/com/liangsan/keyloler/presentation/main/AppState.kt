package com.liangsan.keyloler.presentation.main

import pro.respawn.flowmvi.api.MVIState

data class AppState(
    val currentUser: UserData = UserData(),
    val isDarkTheme: Boolean = false,
    val dynamicColor: Boolean = false,
) : MVIState

data class UserData(
    val uid: String? = null,
    val username: String = "",
    val avatar: String = "",
)
