package com.liangsan.keyloler.presentation.login

data class LoginState(
    val loginMethod: LoginMethod = LoginMethod.PhoneVerification
) {
    sealed class LoginMethod {
        data object Password : LoginMethod()

        data object PhoneVerification : LoginMethod()
    }
}
