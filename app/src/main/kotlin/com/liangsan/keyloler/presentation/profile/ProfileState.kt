package com.liangsan.keyloler.presentation.profile

data class ProfileState(
    val loggedIn: Boolean = false,
    val userNickname: String = "",
    val userAvatar: String = ""
)