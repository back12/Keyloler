package com.liangsan.keyloler.presentation.profile.profile

import com.liangsan.keyloler.domain.model.Thread

data class ProfileState(
    val uid: String? = null,
    val userNickname: String = "",
    val userAvatar: String = "",
    val threadHistoryOverView: List<Thread> = emptyList()
)