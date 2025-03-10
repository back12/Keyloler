package com.liangsan.keyloler.presentation.thread

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.onGloballyPositioned
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
import com.liangsan.keyloler.presentation.component.AnimatedProgressIndicator
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HtmlRichText
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString
import me.saket.telephoto.zoomable.coil3.ZoomableAsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ThreadScreen(
    modifier: Modifier = Modifier,
    tid: String,
    title: String,
    viewModel: ThreadViewModel = koinViewModel { parametersOf(tid) },
    onNavigateUp: () -> Unit
) {
    val threadContent by viewModel.state.collectAsStateWithLifecycle()
    ThreadScreenContent(
        modifier = modifier,
        title = title,
        content = threadContent,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ThreadScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    content: Result<ThreadContent>,
    onNavigateUp: () -> Unit
) {
    var zoomImageSrc by remember { mutableStateOf<String?>(null) }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    BackHandler(enabled = zoomImageSrc != null) {
        zoomImageSrc = null
    }
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    val completeTitle = content.data?.thread?.subject ?: title
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
                .padding(padding)
                .background(MaterialTheme.colorScheme.surfaceContainer)
        ) {
            val lazyListState = rememberLazyListState()
            var firstPostHeight by remember { mutableFloatStateOf(0f) }
            var firstPostOffset by remember { mutableFloatStateOf(0f) }
            val postNestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        if (available.y > 0 && lazyListState.firstVisibleItemIndex > 0)
                            return super.onPreScroll(available, source)
                        val delta = available.y
                        val newOffset = firstPostOffset + delta
                        if (lazyListState.canScrollForward)
                            firstPostOffset = newOffset.coerceIn(-firstPostHeight, 0f)

                        return super.onPreScroll(available, source)
                    }
                }
            }
            content.onSuccess { data ->
                val postList = data.postList
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(postNestedScrollConnection),
                    state = lazyListState
                ) {
                    // Make first post sticky to prevent lazy load to increase performance
                    // in case first post contains lots of data
                    stickyHeader {
                        Column(
                            modifier = Modifier
                                .graphicsLayer { translationY = firstPostOffset }
                                .onGloballyPositioned {
                                    firstPostHeight = it.size.height.toFloat()
                                }
                        ) {
                            postList.firstOrNull()?.let {
                                PostItem(post = it) {
                                    zoomImageSrc = it
                                }
                                Divider()
                            }
                        }
                    }
                    if (postList.isNotEmpty()) {
                        items(
                            postList.size - 1,
                            key = { index -> postList[index + 1].pid }
                        ) { index ->
                            Column {
                                PostItem(post = postList[index + 1]) {
                                    zoomImageSrc = it
                                }
                                Divider()
                            }
                        }
                    }
                }
            }
            AnimatedProgressIndicator(content.isLoading())
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
    onZoomImage: (String?) -> Unit
) {
    Column(
        modifier = modifier.padding(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
        ) {
            Avatar(avatarUrl = getAvatarUrl(post.authorId))
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