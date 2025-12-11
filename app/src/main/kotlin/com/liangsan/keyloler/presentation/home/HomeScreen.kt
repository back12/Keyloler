package com.liangsan.keyloler.presentation.home

import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope.ResizeMode.Companion.RemeasureToBounds
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalCenteredHeroCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.liangsan.keyloler.domain.model.Slide
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.ForumBadge
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onOpenThread: (String, String) -> Unit
) {
    with(viewModel.store) {
        val state by subscribe()
        HomeScreenContent(
            modifier = modifier
                .safeDrawingPadding()
                .bottomBarPadding(),
            state = state,
            onOpenThread = onOpenThread,
            onSelectTab = { intent(HomeIntent.SelectTab(it)) }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onOpenThread: (String, String) -> Unit,
    onSelectTab: (String) -> Unit
) {
    val sharedTransitionScope = LocalSharedTransitionScope.current
    val animatedVisibilityScope = LocalNavAnimatedVisibilityScope.current
    Crossfade(targetState = state.loading) {
        if (it) {
            CircularProgressIndicator(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
            return@Crossfade
        }
        with(sharedTransitionScope) {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    if (state.slides.isNotEmpty()) {
                        SlideShow(slides = state.slides, onSlideClick = onOpenThread)
                    }
                }

                stickyHeader(contentType = "title") {
                    IndexTabs(
                        tabs = state.tabs,
                        currentTab = state.currentTab,
                        onSelect = onSelectTab
                    )
                }
                items(
                    state.currentThreadList,
                    key = { thread -> thread.tid },
                    contentType = { "thread" }
                ) { thread ->
                    IndexThreadItem(
                        thread = thread,
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

@Composable
private fun IndexThreadItem(
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {
                Avatar(getAvatarUrl(thread.authorId))
                Spacer(Modifier.width(12.dp))
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Text(
                        thread.author,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        thread.dateline,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.weight(1f))
                thread.forum?.let {
                    ForumBadge(it)
                }
            }
            Spacer(Modifier.height(16.dp))
            Text(
                thread.subject,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Divider()
    }
}

@Composable
private fun IndexTabs(
    modifier: Modifier = Modifier,
    tabs: List<String>,
    currentTab: String?,
    onSelect: (String) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(currentTab) {
        val childIndex = tabs.indexOf(currentTab).takeIf { it > -1 } ?: return@LaunchedEffect
        val itemInfo =
            lazyListState.layoutInfo.visibleItemsInfo.firstOrNull { it.index == childIndex }
                ?: return@LaunchedEffect
        val center =
            (lazyListState.layoutInfo.viewportEndOffset - lazyListState.layoutInfo.afterContentPadding) / 2
        val childCenter = itemInfo.offset + itemInfo.size / 2
        lazyListState.animateScrollBy((childCenter - center).toFloat())
    }
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 16.dp, bottom = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        state = lazyListState
    ) {
        items(tabs) { tab ->
            val selected = tab == currentTab
            val tabTransition = updateTransition(selected)
            val textColor = tabTransition.animateColor {
                if (it) MaterialTheme.colorScheme.onSecondary else MaterialTheme.colorScheme.onSurface
            }
            val backgroundColor = tabTransition.animateColor {
                if (it) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceContainer
            }
            val borderColor = tabTransition.animateColor {
                if (it) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.surfaceContainerHighest
            }
            Text(
                tab,
                color = textColor.value,
                fontWeight = if (selected) FontWeight.Bold else null,
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(backgroundColor.value)
                    .border(
                        width = 1.dp,
                        color = borderColor.value,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(onClick = { onSelect(tab) })
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SlideShow(
    modifier: Modifier = Modifier,
    slides: List<Slide>,
    onSlideClick: (String, String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val carouselState = rememberCarouselState(slides.size) { Int.MAX_VALUE }

    HorizontalCenteredHeroCarousel(
        modifier = modifier,
        state = carouselState,
        maxItemWidth = 280.dp,
        itemSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
        flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(state = carouselState)
    ) {
        val slide = slides[it % slides.size]
        Box(
            modifier = Modifier
                .clickable {
                    if (carouselState.currentItem != it) {
                        scope.launch {
                            carouselState.animateScrollToItem(it)
                        }
                        return@clickable
                    }
                    onSlideClick(slide.tid, slide.title)
                }
        ) {
            AsyncImage(
                model = slide.img,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.8f)
            )
            slide.title.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    maxLines = 1,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                        .background(Color(0x9F000000))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}