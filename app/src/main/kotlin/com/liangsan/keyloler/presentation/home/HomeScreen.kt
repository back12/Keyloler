package com.liangsan.keyloler.presentation.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.carousel.CarouselDefaults
import androidx.compose.material3.carousel.HorizontalUncontainedCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.Slide
import com.liangsan.keyloler.domain.model.Thread
import com.liangsan.keyloler.domain.utils.onSuccess
import com.liangsan.keyloler.presentation.component.Avatar
import com.liangsan.keyloler.presentation.component.Divider
import com.liangsan.keyloler.presentation.component.ForumBadge
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.getAvatarUrl
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    onOpenThread: (String, String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreenContent(
        modifier = modifier
            .safeDrawingPadding()
            .bottomBarPadding(),
        state = state,
        onOpenThread = onOpenThread
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onOpenThread: (String, String) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        state.index.onSuccess {
            if (it.slides.isNotEmpty()) {
                item {
                    SlideShow(slides = it.slides, onSlideClick = onOpenThread)
                }
            }
            it.threadsList.forEach { (title, threads) ->
                item(contentType = "spacer") { Spacer(Modifier.height(16.dp)) }
                stickyHeader(contentType = "title") {
                    IndexThreadListTitle(title = title)
                }
                itemsIndexed(
                    threads,
                    key = { index, thread -> "$index$thread" },
                    contentType = { _, _ -> "thread" }
                ) { index, thread ->
                    IndexThreadItem(
                        thread = thread,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
                        divider = {
                            if (index != threads.lastIndex) {
                                Divider()
                            }
                        },
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
private inline fun IndexThreadItem(
    modifier: Modifier = Modifier,
    thread: Thread,
    divider: @Composable () -> Unit,
    noinline onClick: () -> Unit
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
                Column(verticalArrangement = Arrangement.SpaceAround) {
                    Text(
                        thread.author,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Text(
                        thread.dateline,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(Modifier.weight(1f))
                thread.forum?.let {
                    ForumBadge(it)
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                thread.subject,
                style = MaterialTheme.typography.bodyLarge
            )
        }
        divider()
    }
}

@Composable
private fun IndexThreadListTitle(modifier: Modifier = Modifier, title: String) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        TextButton(
            onClick = {}
        ) {
            Text(stringResource(R.string.view_more))
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
    val carouselState = rememberCarouselState { Int.MAX_VALUE }

    HorizontalUncontainedCarousel(
        modifier = modifier,
        state = carouselState,
        itemWidth = 300.dp,
        itemSpacing = 12.dp,
        contentPadding = PaddingValues(horizontal = 16.dp),
        flingBehavior = CarouselDefaults.singleAdvanceFlingBehavior(state = carouselState)
    ) {
        val slide = slides[it % slides.size]
        Box(
            modifier = Modifier.clickable {
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
            Text(
                text = slide.title,
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