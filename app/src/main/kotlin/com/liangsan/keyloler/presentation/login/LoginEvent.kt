package com.liangsan.keyloler.presentation.login

sealed class LoginEvent {

    data object LoginSucceed : LoginEvent()
}