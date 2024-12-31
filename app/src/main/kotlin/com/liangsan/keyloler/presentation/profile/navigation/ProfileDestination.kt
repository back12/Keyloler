package com.liangsan.keyloler.presentation.profile.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class ProfileDestination : KeylolerDestination() {

    @Serializable
    data object Overview : ProfileDestination()

    @Serializable
    class ProfileInfo(
        val uid: String,
        val avatar: String,
        val nickname: String
    ) : ProfileDestination() {
        override val showBottomNav: Boolean = false
    }
}