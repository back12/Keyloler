package com.liangsan.keyloler.presentation.search_index.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.SearchHistory
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.clearFocusOnKeyboardDismiss
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.runningFold
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SearchScreenContent(
        modifier = modifier,
        state = state,
        onSearchInputChange = viewModel::setSearchInput,
        onClearSearchInput = viewModel::clearSearchInput,
        onNavigateUp = onNavigateUp,
        onSearch = viewModel::searchForThreads,
        onSearchHistoryClick = viewModel::searchForThreadsFromHistory,
        onDeleteSearchHistory = viewModel::deleteSearchHistory,
        onClearSearchHistory = viewModel::clearSearchHistory
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun SearchScreenContent(
    modifier: Modifier = Modifier,
    state: SearchState,
    onSearchInputChange: (String) -> Unit,
    onClearSearchInput: () -> Unit,
    onNavigateUp: () -> Unit,
    onSearch: () -> Unit,
    onSearchHistoryClick: (String) -> Unit,
    onDeleteSearchHistory: (SearchHistory) -> Unit,
    onClearSearchHistory: () -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current

    val softwareKeyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
    ) {
        with(sharedTransitionScope) {
            Row(
                modifier = Modifier
                    .sharedBounds(
                        rememberSharedContentState(key = "search_bar"),
                        animatedVisibilityScope = animatedVisibilityScope
                    )
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceContainerHighest)
                    .safeDrawingPadding(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = {
                        softwareKeyboardController?.hide()
                        onNavigateUp()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_arrow_back_24),
                        contentDescription = null
                    )
                }
                SearchBar(
                    modifier = Modifier.weight(1f),
                    searchInput = state.searchInput,
                    onSearchInputChange = onSearchInputChange,
                    onClearSearchInput = onClearSearchInput,
                    onSearch = onSearch
                )
            }
        }
        SearchHistoryList(
            modifier = Modifier.weight(1f),
            searchHistoryList = state.searchHistory,
            onSearchHistoryClick = onSearchHistoryClick,
            onDeleteSearchHistory = onDeleteSearchHistory,
            onClearSearchHistory = onClearSearchHistory
        )
    }
}

@Composable
private fun SearchHistoryList(
    modifier: Modifier,
    searchHistoryList: List<SearchHistory>,
    onSearchHistoryClick: (String) -> Unit,
    onDeleteSearchHistory: (SearchHistory) -> Unit,
    onClearSearchHistory: () -> Unit
) {
    val lazyListState = rememberLazyListState()

    val listSize = rememberUpdatedState(searchHistoryList.size)

    LaunchedEffect(Unit) {
        snapshotFlow { listSize.value }
            .runningFold(0) { oldSize, newSize ->
                if (newSize > oldSize) {
                    // Move to the top when new search history got inserted
                    lazyListState.animateScrollToItem(0)
                }
                newSize
            }.collect()
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = searchHistoryList.isNotEmpty(),
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut()
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth(), state = lazyListState) {
            items(searchHistoryList, key = { it.content }) {
                SearchHistoryItem(
                    modifier = Modifier.animateItem(),
                    searchHistory = it,
                    onClick = { onSearchHistoryClick(it.content) },
                    onDelete = { onDeleteSearchHistory(it) }
                )
            }
            item {
                if (searchHistoryList.isNotEmpty()) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .padding(4.dp),
                        onClick = onClearSearchHistory
                    ) {
                        Text(stringResource(R.string.clear_search_history))
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchBar(
    modifier: Modifier,
    searchInput: String,
    onSearchInputChange: (String) -> Unit,
    onClearSearchInput: () -> Unit,
    onSearch: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    BasicTextField(
        modifier = modifier
            .height(56.dp)
            .focusRequester(focusRequester)
            .clearFocusOnKeyboardDismiss(),
        value = searchInput,
        onValueChange = onSearchInputChange,
        decorationBox = { innerTextField ->
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 8.dp)
            ) {
                if (searchInput.isEmpty()) {
                    Text(
                        stringResource(R.string.search_placeholder_text),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    IconButton(
                        onClick = onClearSearchInput,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.round_close_24),
                            contentDescription = stringResource(R.string.clear_search_bar_input)
                        )
                    }
                }

                innerTextField()
            }
        },
        textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() })
    )
}

@Composable
private fun SearchHistoryItem(
    modifier: Modifier = Modifier,
    searchHistory: SearchHistory,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(top = 3.dp, bottom = 3.dp, start = 36.dp, end = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            searchHistory.content,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = onDelete
        ) {
            Icon(
                painter = painterResource(R.drawable.round_close_24),
                contentDescription = stringResource(R.string.delete_this_search_history),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}