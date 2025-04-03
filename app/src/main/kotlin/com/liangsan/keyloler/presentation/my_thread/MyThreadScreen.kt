package com.liangsan.keyloler.presentation.my_thread

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.TopBar
import com.liangsan.keyloler.presentation.utils.toHTMLAnnotatedString
import org.koin.androidx.compose.koinViewModel

@Composable
fun MyThreadScreen(
    modifier: Modifier = Modifier,
    viewModel: MyThreadViewModel = koinViewModel(),
    onOpenThread: (String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val myThread = viewModel.myThread.collectAsLazyPagingItems()

    MyThreadScreenContent(
        modifier = modifier,
        myThread = myThread,
        onOpenThread = onOpenThread,
        onNavigateUp = onNavigateUp
    )
}

@Composable
private fun MyThreadScreenContent(
    modifier: Modifier = Modifier,
    myThread: LazyPagingItems<Thread>,
    onOpenThread: (String, String) -> Unit,
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
            items(myThread.itemCount, key = myThread.itemKey { it.tid }) { index ->
                myThread[index]?.let {
                    MyThreadItem(
                        thread = it,
                        onClick = {
                            onOpenThread(it.tid, it.subject)
                        },
                        modifier = Modifier
                            .padding(
                                horizontal = 16.dp,
                                vertical = 12.dp
                            )
                            .animateItem()
                    )
                    if (index != myThread.itemCount - 1) {
                        Divider()
                    }
                }
            }
        }
    }
}

@Composable
private fun MyThreadItem(
    modifier: Modifier = Modifier,
    thread: Thread,
    onClick: () -> Unit
) {
    Column {
        Column(
            modifier = Modifier
                .clickable(onClick = onClick)
                .then(modifier)
        ) {
            Text(
                thread.subject,
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(16.dp))
            Text(
                thread.dateline.toHTMLAnnotatedString(),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Divider()
    }
}