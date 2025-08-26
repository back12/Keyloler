package com.liangsan.keyloler.presentation.profile.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HistoryItem
import com.liangsan.keyloler.presentation.main.UserData
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.onTap
import org.koin.androidx.compose.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    currentUser: UserData,
    onNavigateToLogin: () -> Unit,
    onNavigateToProfileInfo: () -> Unit,
    onNavigateToThreadHistory: () -> Unit,
    onOpenThread: (String, String) -> Unit,
    onNavigateToMyThread: () -> Unit,
    onNavigateToNotice: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val state by viewModel.store.subscribe()
    ProfileScreenContent(
        modifier = modifier.bottomBarPadding(),
        state = state,
        uid = currentUser.uid,
        username = currentUser.username,
        avatar = currentUser.avatar,
        onTopBarClick = {
            if (currentUser.uid != null) {
                onNavigateToProfileInfo()
            } else {
                onNavigateToLogin()
            }
        },
        onNavigateToThreadHistory = onNavigateToThreadHistory,
        onOpenThread = onOpenThread,
        onNavigateToMyThread = onNavigateToMyThread,
        onNavigateToNotice = onNavigateToNotice,
        onNavigateToSettings = onNavigateToSettings
    )
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    state: ProfileState,
    uid: String?,
    username: String,
    avatar: String,
    onTopBarClick: () -> Unit,
    onNavigateToThreadHistory: () -> Unit,
    onOpenThread: (String, String) -> Unit,
    onNavigateToMyThread: () -> Unit,
    onNavigateToNotice: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            ProfileTopBar(
                avatarUrl = avatar,
                title = if (uid != null) username else stringResource(R.string.not_logged_in),
                onTopBarClick = onTopBarClick
            )
        }
        item {
            Column(
                modifier = Modifier.padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(stringResource(R.string.history))
                    TextButton(
                        onClick = onNavigateToThreadHistory
                    ) {
                        Text(stringResource(R.string.view_all))
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.threadHistoryOverView.isEmpty()) {
                        Text(
                            stringResource(R.string.blank)
                        )
                    } else {
                        state.threadHistoryOverView.forEachIndexed { index, thread ->
                            HistoryItem(thread = thread, onClick = {
                                onOpenThread(thread.tid, thread.subject)
                            })
                            if (index != state.threadHistoryOverView.lastIndex) {
                                Divider()
                            }
                        }
                    }
                }
            }
        }
        item {
            IconTextButton(
                text = stringResource(R.string.my_thread),
                icon = painterResource(R.drawable.round_description_24),
                onClick = onNavigateToMyThread
            )
        }
        item {
            IconTextButton(
                text = stringResource(R.string.message_notification),
                icon = painterResource(R.drawable.round_notifications_none_24),
                onClick = onNavigateToNotice
            )
        }
        item {
            IconTextButton(
                text = stringResource(R.string.setting),
                icon = painterResource(R.drawable.round_settings_24),
                onClick = onNavigateToSettings
            )
        }
    }
}

@Composable
private fun ProfileTopBar(
    avatarUrl: String,
    title: String,
    onTopBarClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(96.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .onTap(onClick = onTopBarClick),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Avatar(avatarUrl)
            Spacer(Modifier.width(12.dp))
            Text(
                title,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun IconTextButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Text(text)
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent(
        state = ProfileState(),
        uid = null,
        username = "",
        avatar = "",
        onNavigateToMyThread = {},
        onNavigateToNotice = {},
        onNavigateToThreadHistory = {},
        onOpenThread = { _, _ -> },
        onTopBarClick = {},
        onNavigateToSettings = {}
    )
}