package com.liangsan.keyloler.presentation.login.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class LoginDestination : KeylolerDestination() {
    @Serializable
    data class Login(
        override val showBottomNav: Boolean = false
    ) : LoginDestination()
}