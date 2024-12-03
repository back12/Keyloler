package com.liangsan.keyloler.presentation.login

import androidx.compose.runtime.Stable
import com.liangsan.keyloler.domain.model.LoginResult

@Stable
data class LoginState(
    val fields: LoginFields = LoginFields.Password(),
    val result: LoginResult? = null,
    val session: LoginSecureSession? = null
) {
    @Stable
    sealed class LoginFields {

        @Stable
        data class Password(
            val username: String = "",
            val password: String = "",
            val secCode: String = "",
            val usernameError: Boolean = false,
            val passwordError: Boolean = false,
            val secCodeError: Boolean = false,
        ) : LoginFields()

        @Stable
        data class PhoneVerification(
            val phone: String = "",
            val code: String = "",
            val phoneError: Boolean = false,
            val codeError: Boolean = false,
        ) : LoginFields()
    }
}
