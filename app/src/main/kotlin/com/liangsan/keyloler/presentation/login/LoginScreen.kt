package com.liangsan.keyloler.presentation.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.network.NetworkHeaders
import coil3.network.httpHeaders
import coil3.request.ImageRequest
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.LoginResult
import com.liangsan.keyloler.presentation.utils.ObserveAsEvents
import com.liangsan.keyloler.presentation.utils.noCopyPasteClipboardManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateUp: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.event) { event ->
        when (event) {
            LoginEvent.LoginSucceed -> onNavigateToHome()
        }
    }

    LoginScreenContent(
        modifier = modifier,
        state = state,
        onNavigateUp = onNavigateUp,
        onChangeLoginMethod = viewModel::changeLoginMethod,
        onUsernameChange = viewModel::setUsername,
        onPasswordChange = viewModel::setPassword,
        onChangeSecCode = viewModel::setSecCode,
        onPhoneChange = viewModel::setPhone,
        onCodeChange = viewModel::setCode,
        onLogin = viewModel::login
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreenContent(
    modifier: Modifier = Modifier,
    state: LoginState,
    onNavigateUp: () -> Unit,
    onChangeLoginMethod: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onChangeSecCode: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
    onLogin: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.login))
                },
                navigationIcon = {
                    IconButton(
                        onClick = onNavigateUp
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_arrow_back_24),
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        }) { padding ->
        ConstraintLayout(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
                .imePadding()
        ) {
            val (logo, card) = createRefs()
            AsyncImage(
                "https://keylol.com/static/image/common/logo.png",
                contentDescription = stringResource(R.string.logo),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(240.dp)
                    .constrainAs(logo) {
                        centerHorizontallyTo(parent)
                        top.linkTo(parent.top)
                        bottom.linkTo(card.top)
                    }
            )
            Card(
                modifier = Modifier
                    .widthIn(max = 500.dp)
                    .constrainAs(card) {
                        centerTo(parent)
                    }
            ) {
                AnimatedContent(
                    state.fields,
                    contentKey = {
                        it::class   // Change content only when the class change, not when the value change.
                    },
                    label = "LoginMethodChangeAnimation"
                ) {
                    when (it) {
                        is LoginState.LoginFields.Password -> LoginByPasswordPart(
                            loginByPassword = it,
                            loading = state.result == LoginResult.Loading,
                            secCode = state.session?.secCodeImage?.value,
                            onChangeUsername = onUsernameChange,
                            onChangePassword = onPasswordChange,
                            onChangeSecCode = onChangeSecCode,
                            onChangeLoginMethod = onChangeLoginMethod,
                            onLogin = onLogin
                        )

                        is LoginState.LoginFields.PhoneVerification -> LoginByPhonePart(
                            loginByPhone = it,
                            loading = state.result == LoginResult.Loading,
                            onChangePhone = onPhoneChange,
                            onChangeCode = onCodeChange,
                            onChangeLoginMethod = onChangeLoginMethod,
                            onGetPhoneVerificationCode = {},
                            onLogin = onLogin
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginByPhonePart(
    modifier: Modifier = Modifier,
    loginByPhone: LoginState.LoginFields.PhoneVerification,
    loading: Boolean,
    onChangePhone: (String) -> Unit,
    onChangeCode: (String) -> Unit,
    onChangeLoginMethod: () -> Unit,
    onGetPhoneVerificationCode: () -> Unit,
    onLogin: () -> Unit
) {
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = loginByPhone.phone,
            onValueChange = onChangePhone,
            label = {
                if (loginByPhone.phoneError) {
                    Text(stringResource(R.string.phone_number_error))
                } else {
                    Text(stringResource(R.string.phone_number))
                }
            },
            isError = loginByPhone.phoneError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = loginByPhone.code,
                onValueChange = onChangeCode,
                label = {
                    if (loginByPhone.codeError) {
                        Text(stringResource(R.string.phone_verification_code_error))
                    } else {
                        Text(stringResource(R.string.phone_verification_code))
                    }
                },
                isError = loginByPhone.codeError,
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            TextButton(
                enabled = !loading,
                onClick = onGetPhoneVerificationCode
            ) {
                Text(stringResource(R.string.get_phone_verification_code))
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onLogin,
            enabled = !loading,
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    stringResource(R.string.login),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        TextButton(enabled = !loading, onClick = onChangeLoginMethod) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.round_password_24),
                    contentDescription = null
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    stringResource(R.string.login_by_password),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
private fun LoginByPasswordPart(
    modifier: Modifier = Modifier,
    loginByPassword: LoginState.LoginFields.Password,
    loading: Boolean,
    secCode: String? = null,
    onChangeUsername: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeSecCode: (String) -> Unit,
    onChangeLoginMethod: () -> Unit,
    onLogin: () -> Unit
) {
    val icon =
        AnimatedImageVector.animatedVectorResource(R.drawable.visibility_anim)
    var showPlainPassword by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = loginByPassword.username,
            onValueChange = onChangeUsername,
            label = {
                if (loginByPassword.usernameError) {
                    Text(stringResource(R.string.password_login_placeholder_error))
                } else {
                    Text(stringResource(R.string.password_login_placeholder))
                }
            },
            isError = loginByPassword.usernameError,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        CompositionLocalProvider(LocalClipboard provides noCopyPasteClipboardManager()) {
            OutlinedTextField(
                value = loginByPassword.password,
                onValueChange = onChangePassword,
                label = {
                    if (loginByPassword.passwordError) {
                        Text(stringResource(R.string.password_error))
                    } else {
                        Text(stringResource(R.string.password))
                    }
                },
                isError = loginByPassword.passwordError,
                singleLine = true,
                visualTransformation = if (showPlainPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = if (secCode != null) ImeAction.Next else ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPlainPassword = !showPlainPassword
                        }
                    ) {
                        Image(
                            painter = rememberAnimatedVectorPainter(icon, !showPlainPassword),
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        secCode?.let {
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
            ) {
                OutlinedTextField(
                    value = loginByPassword.secCode,
                    onValueChange = onChangeSecCode,
                    label = {
                        if (loginByPassword.secCodeError) {
                            Text(stringResource(R.string.secure_code_error))
                        } else {
                            Text(stringResource(R.string.secure_code))
                        }
                    },
                    isError = loginByPassword.secCodeError,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier.weight(3f)
                )
                AsyncImage(
                    model = getSecureCodeImageRequest(it),
                    contentDescription = stringResource(R.string.secure_code),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(2f)
                        .padding(top = 8.dp, start = 12.dp)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = onLogin,
            shape = RoundedCornerShape(6.dp),
            enabled = !loading,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (loading) {
                CircularProgressIndicator(
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text(
                    stringResource(R.string.login),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
        Spacer(Modifier.height(24.dp))
        TextButton(enabled = !loading, onClick = onChangeLoginMethod) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(painter = painterResource(R.drawable.sms), contentDescription = null)
                Spacer(Modifier.width(6.dp))
                Text(
                    stringResource(R.string.login_by_phone),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun getSecureCodeImageRequest(url: String): ImageRequest {
    val headers = NetworkHeaders.Builder()
        .set("Referer", "https://keylol.com/member.php?mod=logging&action=login")
        .build()
    val context = LocalContext.current
    return remember(url) {
        ImageRequest.Builder(context)
            .data(url)
            .httpHeaders(headers)
            .build()
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginByPasswordPartPreview() {
    LoginByPasswordPart(
        loginByPassword = LoginState.LoginFields.Password(
            username = "",
            password = "",
            secCode = ""
        ),
        loading = false,
        onChangeUsername = {},
        onChangePassword = {},
        onChangeSecCode = {},
        onChangeLoginMethod = {},
        onLogin = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun LoginByPhonePartPreview() {
    LoginByPhonePart(
        loginByPhone = LoginState.LoginFields.PhoneVerification(
            phone = "",
            code = ""
        ),
        loading = false,
        onChangePhone = {},
        onChangeCode = {},
        onChangeLoginMethod = {},
        onGetPhoneVerificationCode = {},
        onLogin = {}
    )
}