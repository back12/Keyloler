package com.liangsan.keyloler.domain.model

sealed class LoginResult(open val msg: String? = null) {

    data class Success(override val msg: String?) : LoginResult()

    data class Failed(val cause: Cause) : LoginResult()

    data class NeedVerification(val auth: String, override val msg: String?) : LoginResult()

    data class Error(val throwable: Throwable) : LoginResult()

    data object Loading : LoginResult()

    sealed class Cause {
        data object WrongSecureCode: Cause()

        data object WrongPassword: Cause()

        data class Unknown(val msg: String? = null): Cause()
    }
}