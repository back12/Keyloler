package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun Avatar(avatarUrl: String, modifier: Modifier = Modifier, size: Dp = 36.dp) {
    val avatarModifier = modifier
        .size(size)
        .clip(CircleShape)
        .border(
            width = 1.dp,
            color = MaterialTheme.colorScheme.outline,
            shape = CircleShape
        )
    if (avatarUrl.isBlank()) {
        Spacer(
            modifier = avatarModifier
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            Color.LightGray
                        )
                    )
                )
        )
    } else {
        AsyncImage(
            model = avatarUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = avatarModifier
        )
    }
}