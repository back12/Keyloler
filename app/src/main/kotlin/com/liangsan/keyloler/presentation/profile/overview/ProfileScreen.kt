package com.liangsan.keyloler.presentation.profile.overview

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.onTap
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = koinViewModel(),
    onNavigateToLogin: () -> Unit,
    onNavigateToProfileInfo: (uid: String, avatar: String, nickname: String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreenContent(
        modifier = modifier.bottomBarPadding(),
        state = state,
        onTopBarClick = {
            if (state.uid != null) {
                onNavigateToProfileInfo(state.uid!!, state.userAvatar, state.userNickname)
            } else {
                onNavigateToLogin()
            }
        }
    )
}

@Composable
private fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onTopBarClick: () -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        item {
            ProfileTopBar(
                avatarUrl = state.userAvatar,
                title = if (state.uid != null) state.userNickname else "当前未登录",
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
                    Text("历史记录")
                    TextButton(
                        onClick = {}
                    ) {
                        Text("查看全部")
                    }
                }
                Text(
                    "空",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 8.dp)
                        .wrapContentWidth()
                )
            }
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
                    Text("收藏")
                    TextButton(
                        onClick = {}
                    ) {
                        Text("查看全部")
                    }
                }
                Text(
                    "空",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(vertical = 8.dp)
                        .wrapContentWidth()
                )
            }
        }
        item {
            IconTextButton(
                text = "我的帖子",
                icon = painterResource(R.drawable.round_description_24),
                onClick = {}
            )
        }
        item {
            IconTextButton(
                text = "消息通知",
                icon = painterResource(R.drawable.round_notifications_none_24),
                onClick = {}
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
    val avatarModifier = Modifier
        .size(36.dp)
        .clip(CircleShape)
        .border(
            width = 1.dp,
            color = Color(0xAFFFFFFF),
            shape = CircleShape
        )
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
            if (avatarUrl.isBlank()) {
                Spacer(
                    modifier = avatarModifier
                        .background(
                            brush = Brush.linearGradient(
                                listOf(
                                    MaterialTheme.colorScheme.primary,
                                    Color.LightGray
                                )
                            )
                        )
                )
            } else {
                AsyncImage(
                    model = avatarUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = avatarModifier
                )
            }
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