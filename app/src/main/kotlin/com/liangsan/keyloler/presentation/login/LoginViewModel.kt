package com.liangsan.keyloler.presentation.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun changeLoginMethod() {
        _state.update {
            val new = when (it.loginMethod) {
                LoginState.LoginMethod.Password -> LoginState.LoginMethod.PhoneVerification
                LoginState.LoginMethod.PhoneVerification -> LoginState.LoginMethod.Password
            }
            it.copy(loginMethod = new)
        }
    }
}