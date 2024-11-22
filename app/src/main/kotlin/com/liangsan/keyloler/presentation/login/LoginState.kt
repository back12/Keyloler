package com.liangsan.keyloler.presentation.login

import androidx.compose.runtime.Stable
import com.liangsan.keyloler.domain.utils.Result

@Stable
data class LoginState(
    val loginMethod: LoginMethod = LoginMethod.PhoneVerification(),
    val loginResult: Result<Boolean>? = null
) {
    @Stable
    sealed class LoginMethod {
        @Stable
        data class Password(
            val username: String = "",
            val password: String = ""
        ) : LoginMethod()

        @Stable
        data class PhoneVerification(
            val phone: String = "",
            val code: String = ""
        ) : LoginMethod()
    }
}
