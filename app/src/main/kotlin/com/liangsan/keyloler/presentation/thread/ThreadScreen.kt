package com.liangsan.keyloler.presentation.thread

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Post
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HtmlRichText
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import com.liangsan.keyloler.presentation.utils.onTap
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage
import org.koin.androidx.compose.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
fun ThreadScreen(
    modifier: Modifier = Modifier,
    tid: String,
    title: String,
    viewModel: ThreadViewModel = koinViewModel(),
    onNavigateToProfileInfo: (uid: String, avatar: String, nickname: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val lazyListState = rememberLazyListState()
    val state by viewModel.store.subscribe { action ->
        when (action) {
            is ThreadAction.JumpToPost -> lazyListState.scrollToItem(action.index)
        }
    }
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
    val transitionComplete = remember {
        derivedStateOf {
            animatedVisibilityScope.transition.currentState == EnterExitState.Visible
        }
    }

    ThreadScreenContent(
        modifier = modifier,
        tid = tid,
        title = title,
        transitionComplete = transitionComplete.value,
        state = state,
        lazyListState = lazyListState,
        onNavigateToProfileInfo = onNavigateToProfileInfo,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class, ExperimentalHazeMaterialsApi::class
)
@Composable
private fun ThreadScreenContent(
    modifier: Modifier = Modifier,
    tid: String,
    title: String,
    transitionComplete: Boolean,
    state: ThreadState,
    lazyListState: LazyListState,
    onNavigateToProfileInfo: (uid: String, avatar: String, nickname: String) -> Unit,
    onNavigateUp: () -> Unit
) {
    var zoomImageSrc by remember { mutableStateOf<String?>(null) }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    val hazeState = rememberHazeState()

    BackHandler(enabled = zoomImageSrc != null) {
        zoomImageSrc = null
    }
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                modifier = Modifier.hazeEffect(hazeState, style = HazeMaterials.regular()),
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                title = {
                    val completeTitle =
                        (state as? ThreadState.DisplayThread)?.content?.thread?.subject
                            ?: title
                    Text(
                        completeTitle,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
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
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            AnimatedContent(targetState = state) {
                when (it) {
                    is ThreadState.DisplayThread if transitionComplete -> {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .hazeSource(hazeState),
                            state = lazyListState,
                            contentPadding = padding
                        ) {
                            itemsIndexed(
                                it.content.postList,
                                key = { _, p -> p.pid },
                            ) { _, p ->
                                Column {
                                    PostItem(
                                        post = p,
                                        onZoomImage = { zoomImageSrc = it },
                                        onOpenProfile = {
                                            onNavigateToProfileInfo(
                                                p.authorId,
                                                getAvatarUrl(p.authorId),
                                                p.author
                                            )
                                        }
                                    )
                                    Divider()
                                }
                            }
                        }
                    }

                    else -> {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize()
                        )
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
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Avatar(
                avatarUrl = getAvatarUrl(post.authorId),
                modifier = Modifier.onTap(onOpenProfile)
            )
            Spacer(Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.onTap(onOpenProfile)
            ) {
                Text(
                    post.author,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    post.dateline.toHTMLAnnotatedString(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                )
            }
            Spacer(Modifier.weight(1f))
            Text(
                "#${post.number}",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
        }
        Spacer(Modifier.height(12.dp))
        HtmlRichText(content = post.message, onZoomImage = onZoomImage)
        Spacer(Modifier.height(12.dp))
    }
}