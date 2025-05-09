package com.liangsan.keyloler.presentation.thread

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.RemeasureToBounds
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Post
import com.liangsan.keyloler.domain.model.ThreadContent
import com.liangsan.keyloler.domain.utils.Result
import com.liangsan.keyloler.domain.utils.data
import com.liangsan.keyloler.domain.utils.onSuccess
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HtmlRichText
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.ObserveAsEvents
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import com.liangsan.keyloler.presentation.utils.onTap
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThreadScreen(
    modifier: Modifier = Modifier,
    tid: String,
    title: String,
    viewModel: ThreadViewModel = koinViewModel(),
    onNavigateToProfileInfo: (uid: String, avatar: String, nickname: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val threadContent by viewModel.state.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()

    ObserveAsEvents(viewModel.event) {
        when (it) {
            is ThreadScreenEvent.JumpToPost -> {
                lazyListState.scrollToItem(it.index)
            }
        }
    }

    ThreadScreenContent(
        modifier = modifier,
        tid = tid,
        title = title,
        content = threadContent,
        lazyListState = lazyListState,
        onNavigateToProfileInfo = onNavigateToProfileInfo,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
private fun ThreadScreenContent(
    modifier: Modifier = Modifier,
    tid: String,
    title: String,
    content: Result<ThreadContent>,
    lazyListState: LazyListState,
    onNavigateToProfileInfo: (uid: String, avatar: String, nickname: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
    var zoomImageSrc by remember { mutableStateOf<String?>(null) }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BackHandler(enabled = zoomImageSrc != null) {
        zoomImageSrc = null
    }
    with(sharedTransitionScope) {
        Scaffold(
            modifier = modifier
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
                .sharedBounds(
                    rememberSharedContentState(key = "thread${tid}"),
                    animatedVisibilityScope = animatedVisibilityScope,
                    resizeMode = RemeasureToBounds
                ),
            topBar = {
                LargeTopAppBar(
                    title = {
                        val completeTitle = content.data?.thread?.subject ?: title
                        Text(
                            completeTitle,
                            style = MaterialTheme.typography.titleLarge,
                            overflow = TextOverflow.Ellipsis
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.surfaceContainer)
            ) {
                Crossfade(targetState = content) {
                    if (it.isLoading()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize()
                        )
                        return@Crossfade
                    }
                    content.onSuccess { data ->
                        LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                            items(data.postList, key = { it.pid }) {
                                Column {
                                    PostItem(
                                        post = it,
                                        onZoomImage = { zoomImageSrc = it },
                                        onOpenProfile = {
                                            onNavigateToProfileInfo(
                                                it.authorId,
                                                getAvatarUrl(it.authorId),
                                                it.author
                                            )
                                        }
                                    )
                                    Divider()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    AnimatedVisibility(zoomImageSrc != null) {
        ZoomableAsyncImage(
            model = zoomImageSrc,
            contentDescription = null,
            onClick = {
                zoomImageSrc = null
            },
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        )
    }
}

@Composable
private fun PostItem(
    modifier: Modifier = Modifier,
    post: Post,
    onZoomImage: (String?) -> Unit,
    onOpenProfile: () -> Unit,
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .onTap(onOpenProfile)
        ) {
            Avatar(
                avatarUrl = getAvatarUrl(post.authorId)
            )
            Spacer(Modifier.width(12.dp))
            Column(verticalArrangement = Arrangement.SpaceAround) {
                Text(
                    post.author,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    post.dateline.toHTMLAnnotatedString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        HtmlRichText(content = post.message, onZoomImage = onZoomImage)
    }
}