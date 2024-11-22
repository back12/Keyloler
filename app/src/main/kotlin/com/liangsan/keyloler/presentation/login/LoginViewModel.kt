package com.liangsan.keyloler.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())

    val state: StateFlow<LoginState> = _state.asStateFlow()

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val PHONE_NUMBER = "phone_number"
        const val VERIFICATION_CODE = "verification_code"
    }

    init {
        viewModelScope.launch {
            combine(
                savedStateHandle.getStateFlow(USERNAME, ""),
                savedStateHandle.getStateFlow(PASSWORD, ""),
                savedStateHandle.getStateFlow(PHONE_NUMBER, ""),
                savedStateHandle.getStateFlow(VERIFICATION_CODE, "")
            ) { username, password, phone, code ->
                when (state.value.loginMethod) {
                    is LoginState.LoginMethod.Password -> LoginState.LoginMethod.Password(
                        username = username,
                        password = password
                    )

                    is LoginState.LoginMethod.PhoneVerification -> LoginState.LoginMethod.PhoneVerification(
                        phone = phone,
                        code = code
                    )
                }
            }.collectLatest { method ->
                _state.update {
                    it.copy(loginMethod = method)
                }
            }
        }
    }

    fun changeLoginMethod() {
        _state.update {
            val method = when (it.loginMethod) {
                is LoginState.LoginMethod.Password -> LoginState.LoginMethod.PhoneVerification()
                is LoginState.LoginMethod.PhoneVerification -> LoginState.LoginMethod.Password()
            }
            it.copy(loginMethod = method)
        }
    }

    fun setUsername(username: String) {
        savedStateHandle[USERNAME] = username
    }

    fun setPassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    fun setPhone(phone: String) {
        savedStateHandle[PHONE_NUMBER] = phone
    }

    fun setCode(code: String) {
        savedStateHandle[VERIFICATION_CODE] = code
    }
}