package com.liangsan.keyloler.presentation.profile.profile_info

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.liangsan.keyloler.R
import com.liangsan.keyloler.data.remote.dto.Group
import com.liangsan.keyloler.data.remote.dto.Medal
import com.liangsan.keyloler.domain.model.ProfileInfo
import com.liangsan.keyloler.presentation.theme.LightBlue
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
fun ProfileInfoScreen(
    modifier: Modifier = Modifier,
    uid: String,
    avatar: String,
    nickname: String,
    onNavigateUp: () -> Unit
) {
    val viewModel: ProfileInfoViewModel = koinViewModel {
        parametersOf(uid)
    }
    val state by viewModel.store.subscribe()

    ProfileInfoScreenContent(
        modifier = modifier,
        state = state,
        uid = uid,
        avatar = avatar,
        nickname = nickname,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileInfoScreenContent(
    modifier: Modifier = Modifier,
    state: ProfileInfoState,
    uid: String,
    avatar: String,
    nickname: String,
    onNavigateUp: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        nickname,
                        modifier = Modifier
                            .alpha(topAppBarScrollBehavior.state.overlappedFraction)
                    )
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
        }
    ) { padding ->
        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = avatar,
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(64.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        modifier = Modifier.height(64.dp),
                        verticalArrangement = Arrangement.SpaceAround
                    ) {
                        Text(
                            nickname,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            "uid: $uid",
                            style = MaterialTheme.typography.titleSmall.copy(color = Color.Gray)
                        )
                    }
                }
            }
            state.profile?.let {
                item {
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.padding(top = 12.dp)
                    ) {
                        Row {
                            VerticalTextItem(
                                title = stringResource(R.string.friends),
                                value = it.friends,
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                            VerticalTextItem(
                                title = stringResource(R.string.posts_number),
                                value = (it.posts.toInt() - it.threads.toInt()).toString(),
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                            VerticalTextItem(
                                title = stringResource(R.string.threads_number),
                                value = it.threads,
                                onClick = {},
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
                if (it.medals.isNotEmpty()) {
                    item {
                        MedalWall(medals = it.medals)
                    }
                }
                item {
                    ActivityInfo(info = it)
                }
                item {
                    StatisticInfo(info = it)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
private fun MedalWall(modifier: Modifier = Modifier, medals: List<Medal>) {
    val scope = rememberCoroutineScope()
    Text(
        stringResource(R.string.medal),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(6.dp))
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        FlowRow(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(medals.size) {
                val medal = medals[it]
                val url = "https://keylol.com/static/image/common/${medal.image}"
                val tooltipState = rememberTooltipState()
                TooltipBox(
                    positionProvider = TooltipDefaults.rememberRichTooltipPositionProvider(),
                    tooltip = {
                        RichTooltip(
                            title = {
                                Text(medal.name)
                            }
                        ) {
                            Text(medal.description)
                        }
                    },
                    state = tooltipState
                ) {
                    AsyncImage(
                        model = url,
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .width(140.dp)
                            .clickable { scope.launch { tooltipState.show() } }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActivityInfo(modifier: Modifier = Modifier, info: ProfileInfo) {
    Text(
        stringResource(R.string.activity_info),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(6.dp))
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            if (info.adminGroup.icon.isNotBlank()) {
                UserGroup(group = info.adminGroup)
                Spacer(Modifier.height(12.dp))
            }
            UserGroup(group = info.group)
            Spacer(Modifier.height(12.dp))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextItem(
                    title = stringResource(R.string.online_time),
                    value = stringResource(R.string.suffix_hour, info.onlineTime)
                )
                TextItem(title = stringResource(R.string.reg_date), value = info.regDate)
                TextItem(title = stringResource(R.string.last_visit), value = info.lastVisit)
                TextItem(title = stringResource(R.string.last_activity), value = info.lastActivity)
                TextItem(title = stringResource(R.string.last_post), value = info.lastPost)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatisticInfo(modifier: Modifier = Modifier, info: ProfileInfo) {
    Text(
        stringResource(R.string.statistic_info),
        style = MaterialTheme.typography.titleMedium
    )
    Spacer(Modifier.height(6.dp))
    Card(
        shape = RoundedCornerShape(4.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextItem(
                    title = stringResource(R.string.credits),
                    value = info.credits
                )
                TextItem(
                    title = stringResource(R.string.health_points),
                    value = stringResource(
                        R.string.suffix_points,
                        info.healthPoints
                    )
                )
                TextItem(
                    title = stringResource(R.string.steam_points),
                    value = stringResource(
                        R.string.suffix_grams,
                        info.steamPoints
                    )
                )
                TextItem(
                    title = stringResource(R.string.power_points),
                    value = stringResource(
                        R.string.suffix_points,
                        info.powerPoints
                    )
                )
            }
        }
    }
}

@Composable
private fun UserGroup(modifier: Modifier = Modifier, group: Group) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.user_group),
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
        )
        Text(
            getGroupTitleContent(group.groupTitle),
            style = MaterialTheme.typography.bodySmall
        )
        AsyncImage(
            model = getGroupIcon(group.icon),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(140.dp)
        )
    }
}

@Composable
private fun VerticalTextItem(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .widthIn(min = 80.dp)
            .clickable(
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = false)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(title, style = MaterialTheme.typography.bodySmall)
        Spacer(Modifier.height(8.dp))
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge.copy(color = LightBlue)
        )
    }
}

@Composable
private fun TextItem(modifier: Modifier = Modifier, title: String, value: String) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            title,
            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
            modifier = Modifier.alignByBaseline()
        )
        Spacer(Modifier.width(8.dp))
        Text(
            value,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.alignByBaseline()
        )
    }
}

private fun getGroupTitleContent(title: String?): String {
    if (title == null) return ""

    val regex = ">(.+)<".toRegex()
    return regex.find(title)?.groupValues?.getOrNull(1) ?: title
}

private fun getGroupIcon(icon: String): String {
    val regex = "src=\"(.+?)\"".toRegex()
    return regex.find(icon)?.groupValues?.getOrNull(1) ?: ""
}