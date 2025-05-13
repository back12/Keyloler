package com.liangsan.keyloler.presentation.search_index.index

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Forum
import com.liangsan.keyloler.domain.model.ForumCategory
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import org.koin.androidx.compose.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
fun IndexScreen(
    modifier: Modifier = Modifier,
    viewModel: IndexViewModel = koinViewModel(),
    onSearchClick: () -> Unit,
    onForumClick: (Forum) -> Unit
) {
    val state by viewModel.store.subscribe()

    IndexScreenContent(
        modifier = modifier
            .safeDrawingPadding()
            .bottomBarPadding(),
        state = state,
        onSearchClick = onSearchClick,
        onForumClick = onForumClick
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalFoundationApi::class
)
@Composable
private fun IndexScreenContent(
    modifier: Modifier = Modifier,
    state: IndexState,
    onSearchClick: () -> Unit,
    onForumClick: (Forum) -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
    LazyColumn(modifier = modifier.fillMaxSize()) {
        item {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.search),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            )
        }
        stickyHeader {
            with(sharedTransitionScope) {
                Row(
                    modifier = Modifier
                        .sharedBounds(
                            rememberSharedContentState(key = "search_bar"),
                            animatedVisibilityScope = animatedVisibilityScope
                        )
                        .fillMaxWidth()
                        .height(56.dp)
                        .padding(horizontal = 12.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(bottom = 8.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceContainerHighest,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable(onClick = onSearchClick)
                        .padding(vertical = 6.dp, horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.round_search_24),
                        contentDescription = null
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(
                        stringResource(R.string.search_placeholder_text),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        items(
            state.forumCategoryList,
            key = { item -> item.category.fcid },
            contentType = { _ -> "forum_category" }
        ) { (category, forum) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            ) {
                ForumCategoryItem(
                    modifier = Modifier.animateItem(),
                    category = category,
                    forum = forum,
                    onForumClick = onForumClick
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ForumCategoryItem(
    modifier: Modifier = Modifier,
    category: ForumCategory,
    forum: List<Forum>,
    onForumClick: (Forum) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            category.name,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(6.dp))
        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(4.dp)) {
            FlowRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(forum.size) {
                    val current = forum[it]
                    ForumItem(forum = current, onForumClick = { onForumClick(current) })
                }
            }
        }
    }
}

@Composable
private fun ForumItem(modifier: Modifier = Modifier, forum: Forum, onForumClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable(onClick = onForumClick),
        color = MaterialTheme.colorScheme.secondary,
        contentColor = MaterialTheme.colorScheme.onSecondary,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .heightIn(min = 40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = forum.icon ?: R.drawable.forum,
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(24.dp)
            )
            Spacer(Modifier.width(8.dp))
            Column(verticalArrangement = Arrangement.SpaceAround) {
                Text(forum.name, style = MaterialTheme.typography.titleSmall)
                forum.description?.let {
                    Text(it, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}