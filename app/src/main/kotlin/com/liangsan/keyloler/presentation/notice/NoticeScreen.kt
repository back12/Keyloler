package com.liangsan.keyloler.presentation.notice

import android.text.Html
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Notice
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.TopBar
import com.liangsan.keyloler.presentation.utils.format
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun NoticeScreen(
    modifier: Modifier = Modifier,
    viewModel: NoticeViewModel = koinViewModel(),
    onOpenThread: (String, String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val notice = viewModel.notice.collectAsLazyPagingItems()

    NoticeScreenContent(
        modifier = modifier,
        notice = notice,
        onOpenThread = onOpenThread,
        onNavigateUp = onNavigateUp
    )
}

@Composable
private fun NoticeScreenContent(
    modifier: Modifier = Modifier,
    notice: LazyPagingItems<Notice>,
    onOpenThread: (String, String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onNavigateUp = onNavigateUp
            ) {
                Text(
                    stringResource(R.string.my_thread),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .height(56.dp)
                        .wrapContentHeight()
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 8.dp),
        ) {
            items(notice.itemCount, key = notice.itemKey { it.id }) { index ->
                notice[index]?.let {
                    NoticeItem(
                        notice = it,
                        onOpenThread = onOpenThread,
                        modifier = Modifier
                            .animateItem()
                    )
                }
            }
        }
    }
}

@Composable
private fun NoticeItem(
    modifier: Modifier = Modifier,
    notice: Notice,
    onOpenThread: (String, String, String) -> Unit
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clickable {
                    notice.noteVar?.let { onOpenThread(it.tid, it.subject, it.pid) }
                }
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp
                )
        ) {
            Avatar(getAvatarUrl(notice.authorId))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    notice.dateline.toLong().format(),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(Modifier.height(12.dp))
                Text(Html.fromHtml(notice.note, Html.FROM_HTML_MODE_COMPACT).toString())
            }
        }
        Divider()
    }
}