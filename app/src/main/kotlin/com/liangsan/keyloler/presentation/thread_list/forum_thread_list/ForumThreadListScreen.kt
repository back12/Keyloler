package com.liangsan.keyloler.presentation.thread_list.forum_thread_list

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.RemeasureToBounds
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Forum
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.ThreadItem
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ForumThreadListScreen(
    modifier: Modifier = Modifier,
    forum: Forum,
    viewModel: ForumThreadListViewModel = koinViewModel { parametersOf(forum.fid) },
    onOpenThread: (String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val threadList = viewModel.threadList.collectAsLazyPagingItems()

    ForumThreadListScreenContent(
        modifier = modifier,
        forum = forum,
        threadList = threadList,
        onOpenThread = onOpenThread,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun ForumThreadListScreenContent(
    modifier: Modifier = Modifier,
    forum: Forum,
    threadList: LazyPagingItems<Thread>,
    onOpenThread: (String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            forum.name,
                            style = MaterialTheme.typography.titleLarge,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            forum.description ?: "",
                            style = MaterialTheme.typography.titleSmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
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
        with(sharedTransitionScope) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Divider()
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(
                        threadList.itemCount,
                        key = threadList.itemKey { thread -> thread.tid }
                    ) { index ->
                        val thread = threadList[index]
                        thread?.let {
                            ThreadItem(
                                thread = it,
                                modifier = Modifier
                                    .sharedBounds(
                                        rememberSharedContentState(key = "thread${thread.tid}"),
                                        animatedVisibilityScope = animatedVisibilityScope,
                                        resizeMode = RemeasureToBounds
                                    )
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                onClick = {
                                    onOpenThread(thread.tid, thread.subject)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}