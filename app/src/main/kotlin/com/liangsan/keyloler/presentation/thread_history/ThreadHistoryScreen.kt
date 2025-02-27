package com.liangsan.keyloler.presentation.thread_history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.HistoryItem
import com.liangsan.keyloler.presentation.component.SearchBar
import com.liangsan.keyloler.presentation.component.TopBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun ThreadHistoryScreen(
    modifier: Modifier = Modifier,
    viewModel: ThreadHistoryViewModel = koinViewModel(),
    onOpenThread: (String, String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ThreadHistoryScreenContent(
        modifier = modifier,
        state = state,
        onOpenThread = onOpenThread,
        onSearchInputChange = viewModel::changeSearchInput,
        onNavigateUp = onNavigateUp
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ThreadHistoryScreenContent(
    modifier: Modifier = Modifier,
    state: ThreadHistoryState,
    onOpenThread: (String, String) -> Unit,
    onSearchInputChange: (String) -> Unit,
    onNavigateUp: () -> Unit
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    var showSearchBar by remember { mutableStateOf(false) }
    val historyList = state.historyList.collectAsLazyPagingItems()
    Column(modifier = modifier) {
        TopBar(
            onNavigateUp = {
                softwareKeyboardController?.hide()
                onNavigateUp()
            }
        ) {
            if (showSearchBar) {
                SearchBar(
                    searchInput = state.searchInput,
                    onSearchInputChange = onSearchInputChange,
                    onClearSearchInput = {
                        onSearchInputChange("")
                    },
                    onSearch = { /* No op */ }
                )
            } else {
                Text(
                    stringResource(R.string.history),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .wrapContentHeight()
                )
            }
            if (!showSearchBar) {
                IconButton(
                    onClick = {
                        showSearchBar = true
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_search_24),
                        contentDescription = stringResource(R.string.search)
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(historyList.itemCount, key = historyList.itemKey { it.tid }) { index ->
                historyList[index]?.let {
                    HistoryItem(
                        thread = it,
                        onClick = {
                            onOpenThread(it.tid, it.subject)
                        },
                        modifier = Modifier.animateItem()
                    )
                    if (index != historyList.itemCount - 1) {
                        Divider()
                    }
                }
            }
        }
    }
}