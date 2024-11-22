package com.liangsan.keyloler.presentation.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.utils.NoCopyPasteClipboardManager
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreenContent(
        modifier = modifier,
        state = state,
        onNavigateUp = onNavigateUp,
        onChangeLoginMethod = viewModel::changeLoginMethod,
        onUsernameChange = viewModel::setUsername,
        onPasswordChange = viewModel::setPassword,
        onPhoneChange = viewModel::setPhone,
        onCodeChange = viewModel::setCode
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
    onPhoneChange: (String) -> Unit,
    onCodeChange: (String) -> Unit,
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Card(
                modifier = modifier
                    .align(Alignment.Center)
                    .widthIn(max = 500.dp)
                    .padding(padding)
                    .imePadding()
                    .padding(horizontal = 12.dp, vertical = 24.dp)
            ) {
                AnimatedContent(
                    state.loginMethod,
                    contentKey = {
                        it::class   // Change content only when the class change, not when the value change.
                    },
                    label = "LoginMethodChangeAnimation"
                ) {
                    when (it) {
                        is LoginState.LoginMethod.Password -> LoginByPasswordPart(
                            loginByPassword = it,
                            onChangeUsername = onUsernameChange,
                            onChangePassword = onPasswordChange,
                            onChangeLoginMethod = onChangeLoginMethod
                        )

                        is LoginState.LoginMethod.PhoneVerification -> LoginByPhonePart(
                            loginByPhone = it,
                            onChangePhone = onPhoneChange,
                            onChangeCode = onCodeChange,
                            onChangeLoginMethod = onChangeLoginMethod
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
    loginByPhone: LoginState.LoginMethod.PhoneVerification,
    onChangePhone: (String) -> Unit,
    onChangeCode: (String) -> Unit,
    onChangeLoginMethod: () -> Unit
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
                Text(stringResource(R.string.phone_number))
            },
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
                    Text(stringResource(R.string.phone_verification_code))
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            TextButton(
                onClick = {

                }
            ) {
                Text(stringResource(R.string.get_phone_verification_code))
            }
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {

            },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                stringResource(R.string.login),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(Modifier.height(24.dp))
        TextButton(onClick = onChangeLoginMethod) {
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

@Composable
private fun LoginByPasswordPart(
    modifier: Modifier = Modifier,
    loginByPassword: LoginState.LoginMethod.Password,
    onChangeUsername: (String) -> Unit,
    onChangePassword: (String) -> Unit,
    onChangeLoginMethod: () -> Unit
) {
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
                Text(stringResource(R.string.password_login_placeholder))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(12.dp))
        CompositionLocalProvider(LocalClipboardManager provides NoCopyPasteClipboardManager) {
            OutlinedTextField(
                value = loginByPassword.password,
                onValueChange = onChangePassword,
                label = {
                    Text(stringResource(R.string.password))
                },
                singleLine = true,
                visualTransformation = if (showPlainPassword) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    TextButton(
                        onClick = {
                            showPlainPassword = !showPlainPassword
                        }
                    ) {
                        Text(
                            if (showPlainPassword) "Hide" else "Show"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(Modifier.height(24.dp))
        Button(
            onClick = {

            },
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            Text(
                stringResource(R.string.login),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }
        Spacer(Modifier.height(24.dp))
        TextButton(onClick = onChangeLoginMethod) {
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