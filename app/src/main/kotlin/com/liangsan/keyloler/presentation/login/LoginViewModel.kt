package com.liangsan.keyloler.presentation.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.data.preferences.AppData
import com.liangsan.keyloler.domain.model.LoginResult
import com.liangsan.keyloler.domain.repository.LoginRepository
import com.liangsan.keyloler.domain.repository.UserRepository
import com.liangsan.keyloler.domain.utils.data
import com.liangsan.keyloler.presentation.utils.SnackbarController
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class LoginViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val loginRepository: LoginRepository,
    private val userRepository: UserRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _event = Channel<LoginEvent>()
    val event = _event.receiveAsFlow()

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
        const val SEC_CODE = "secure_code"
        const val PHONE_NUMBER = "phone_number"
        const val VERIFICATION_CODE = "verification_code"
    }

    init {
        observeSavedState()
        observeLoginSuccess()
    }

    fun changeLoginMethod() {
        _state.update {
            val method = when (it.fields) {
                is LoginState.LoginFields.Password -> LoginState.LoginFields.PhoneVerification()
                is LoginState.LoginFields.PhoneVerification -> LoginState.LoginFields.Password()
            }
            it.copy(fields = method)
        }
    }

    fun login() {
        if (state.value.result is LoginResult.Success) {
            return
        }
        state.value.fields.let { fields ->
            when (fields) {
                is LoginState.LoginFields.Password -> {
                    loginByPassword(data = fields)
                }

                is LoginState.LoginFields.PhoneVerification -> loginByPhone(fields)
            }
        }
    }

    @OptIn(ExperimentalEncodingApi::class)
    private fun loginByPassword(data: LoginState.LoginFields.Password) {
        val session = state.value.session
        val usernameError = data.username.isBlank()
        val passwordError = data.password.isBlank()
        val secCodeError = data.secCode.isBlank().takeIf { session != null } ?: false
        _state.update {
            it.copy(
                fields = data.copy(
                    usernameError = usernameError,
                    passwordError = passwordError,
                    secCodeError = secCodeError
                )
            )
        }
        if (usernameError || passwordError || secCodeError) {
            return
        }
        viewModelScope.launch {
            // create a key with username and password to identify if any has changed during login process
            // if changed, then it will need to validate username and password again.
            val sessionKey = Base64.encode(data.username.toByteArray()) +
                    Base64.encode(data.password.toByteArray())

            val loginResult: Flow<LoginResult> = if (session != null) {
                if (session.key == sessionKey) {
                    loginRepository.secureWebLogin(data.secCode, session.loginParam)
                } else {
                    loginRepository.loginByPassword(data.username, data.password)
                }
            } else {
                loginRepository.loginByPassword(data.username, data.password)
            }
            loginResult.collectLatest { result ->
                _state.update {
                    val newSession = when (result) {
                        is LoginResult.NeedVerification -> createLoginSecureSession(
                            result.auth,
                            sessionKey
                        )

                        is LoginResult.Failed -> {
                            when (result.cause) {

                                // keep previous session
                                is LoginResult.Cause.Unknown -> it.session

                                // remove existed session
                                LoginResult.Cause.LoginStrike,
                                LoginResult.Cause.WrongPassword -> null

                                // create a new session with previous auth value
                                LoginResult.Cause.NoUidFound,
                                LoginResult.Cause.WrongSecureCode -> createLoginSecureSession(
                                    it.session!!.loginParam.auth,
                                    it.session.key
                                )
                            }
                        }

                        else -> it.session
                    }
                    it.copy(result = result, session = newSession)
                }
                setSecCode("")
                showSnackbarByResult(result)
            }
        }
    }

    private suspend fun createLoginSecureSession(
        auth: String,
        sessionKey: String
    ): LoginSecureSession? {
        val loginParam = loginRepository.getSecureCodeUpdateParam(auth).data
        return loginParam?.let {
            LoginSecureSession(it, sessionKey).apply {
                secCodeImage.value =
                    loginRepository.getSecureCodeImageUrl(update = it.update, idHash = it.idHash)
            }
        }
    }

    private fun loginByPhone(data: LoginState.LoginFields.PhoneVerification) {
        val phoneError = data.phone.isBlank()
        val codeError = data.code.isBlank()
        _state.update {
            it.copy(fields = data.copy(phoneError = phoneError, codeError = codeError))
        }
        if (phoneError || codeError) {
            return
        }
    }

    private suspend fun showSnackbarByResult(result: LoginResult) {
        when (result) {
            is LoginResult.Error -> {
                SnackbarController.showSnackbar(result.throwable.toString())
            }

            is LoginResult.Failed -> {
                when (val cause = result.cause) {
                    is LoginResult.Cause.Unknown -> cause.msg?.let {
                        SnackbarController.showSnackbar(it)
                    }

                    LoginResult.Cause.WrongPassword ->
                        SnackbarController.showSnackbar("密码填写错误")

                    LoginResult.Cause.WrongSecureCode ->
                        SnackbarController.showSnackbar("验证码填写错误")

                    LoginResult.Cause.NoUidFound ->
                        SnackbarController.showSnackbar("登陆失败，未找到uid")

                    LoginResult.Cause.LoginStrike -> SnackbarController.showSnackbar("密码错误次数过多，请 15 分钟后重新登录")
                }
            }

            else -> {
                result.msg?.let {
                    SnackbarController.showSnackbar(it)
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun observeLoginSuccess() {
        viewModelScope.launch {
            state.map { it.result }
                .filter { it is LoginResult.Success }
                .collectLatest { result ->
                    result as LoginResult.Success
                    userRepository.updateUserData {
                        AppData.newBuilder()
                            .setUid(result.uid)
                            .setLoginTime(Clock.System.now().toEpochMilliseconds())
                            .build()
                    }
                    _event.send(LoginEvent.LoginSucceed)
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeSavedState() {
        viewModelScope.launch {
            state.map { it.fields }
                .distinctUntilChangedBy { it::class }
                .flatMapLatest {
                    when (it) {
                        is LoginState.LoginFields.Password -> {
                            combine(
                                savedStateHandle.getStateFlow(USERNAME, ""),
                                savedStateHandle.getStateFlow(PASSWORD, ""),
                                savedStateHandle.getStateFlow(SEC_CODE, ""),
                            ) { username, password, secCode ->
                                LoginState.LoginFields.Password(
                                    username = username,
                                    password = password,
                                    secCode = secCode,
                                )
                            }
                        }

                        is LoginState.LoginFields.PhoneVerification -> {
                            combine(
                                savedStateHandle.getStateFlow(PHONE_NUMBER, ""),
                                savedStateHandle.getStateFlow(VERIFICATION_CODE, "")
                            ) { phone, code ->
                                LoginState.LoginFields.PhoneVerification(
                                    phone = phone,
                                    code = code
                                )
                            }
                        }
                    }
                }.collectLatest { fields ->
                    _state.update {
                        it.copy(fields = fields)
                    }
                }
        }
    }

    fun setUsername(username: String) {
        savedStateHandle[USERNAME] = username
    }

    fun setPassword(password: String) {
        savedStateHandle[PASSWORD] = password
    }

    fun setSecCode(secCode: String) {
        savedStateHandle[SEC_CODE] = secCode
    }

    fun setPhone(phone: String) {
        savedStateHandle[PHONE_NUMBER] = phone
    }

    fun setCode(code: String) {
        savedStateHandle[VERIFICATION_CODE] = code
    }
}