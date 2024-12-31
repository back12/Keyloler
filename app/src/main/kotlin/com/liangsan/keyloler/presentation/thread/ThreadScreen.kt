package com.liangsan.keyloler.presentation.thread

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
import androidx.compose.foundation.lazy.items
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
import com.liangsan.keyloler.presentation.component.AnimatedProgressIndicator
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HtmlRichText
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ThreadScreen(
    tid: String,
    title: String,
    viewModel: ThreadViewModel = koinViewModel { parametersOf(tid) },
    onNavigateUp: () -> Unit
) {
    val threadContent by viewModel.state.collectAsStateWithLifecycle()
    ThreadScreenContent(title = title, content = threadContent, onNavigateUp = onNavigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThreadScreenContent(
    title: String,
    content: Result<ThreadContent>,
    onNavigateUp: () -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    val completeTitle = content.data?.thread?.subject ?: title
                    Text(completeTitle, maxLines = 2, overflow = TextOverflow.Ellipsis)
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
        Box(modifier = Modifier.padding(padding)) {
            content.onSuccess {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(it.postList, key = { it.pid }) {
                        Column {
                            PostItem(post = it)
                            Divider()
                        }
                    }
                }
            }
            AnimatedProgressIndicator(content.isLoading())
        }
    }
}

@Composable
private fun PostItem(modifier: Modifier = Modifier, post: Post) {
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
                    post.dateline.replace("&nbsp;", " "),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        HtmlRichText(content = post.message)
    }
}