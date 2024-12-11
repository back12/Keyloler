package com.liangsan.keyloler.presentation.profile.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class ProfileDestination : KeylolerDestination() {

    @Serializable
    data object Overview : ProfileDestination()

    @Serializable
    data class ProfileInfo(
        override val showBottomNav: Boolean = false,
        val uid: String,
        val avatar: String,
        val nickname: String
    ) : ProfileDestination()
}