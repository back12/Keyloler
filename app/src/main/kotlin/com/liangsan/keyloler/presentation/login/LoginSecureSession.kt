package com.liangsan.keyloler.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import com.liangsan.keyloler.domain.model.LoginParam

@Stable
data class LoginSecureSession(
    val loginParam: LoginParam,
    val key: String
) {
    val secCodeImage: MutableState<String?> = mutableStateOf(null)
}