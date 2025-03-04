package com.liangsan.keyloler.presentation.thread_history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.liangsan.keyloler.presentation.utils.Zero
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
        onSearchInputChange = viewModel::setSearchInput,
        onClearHistory = viewModel::showDialog,
        onNavigateUp = onNavigateUp
    )

    if (state.showDialog) {
        BasicAlertDialog(
            onDismissRequest = viewModel::dismissDialog
        ) {
            Surface(
                shape = MaterialTheme.shapes.large,
                tonalElevation = AlertDialogDefaults.TonalElevation
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Text(text = stringResource(R.string.confirm_clear_all_history))
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(modifier = Modifier.align(Alignment.End)) {
                        TextButton(
                            onClick = viewModel::dismissDialog
                        ) { Text(stringResource(R.string.cancel)) }
                        Spacer(Modifier.width(24.dp))
                        TextButton(
                            onClick = {
                                viewModel.clearHistory()
                                viewModel.dismissDialog()
                            }
                        ) { Text(stringResource(R.string.confirm)) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ThreadHistoryScreenContent(
    modifier: Modifier = Modifier,
    state: ThreadHistoryState,
    onOpenThread: (String, String) -> Unit,
    onSearchInputChange: (String) -> Unit,
    onClearHistory: () -> Unit,
    onNavigateUp: () -> Unit
) {
    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    var showSearchBar by remember { mutableStateOf(false) }
    val historyList = state.historyList.collectAsLazyPagingItems()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.bottomBarPadding(),
                onClick = onClearHistory
            ) {
                Icon(
                    painter = painterResource(R.drawable.round_delete_outline_24),
                    contentDescription = null
                )
            }
        },
        contentWindowInsets = WindowInsets.Zero
    ) { padding ->
        Column(modifier = modifier.padding(padding)) {
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
                contentPadding = PaddingValues(vertical = 8.dp),
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
}