package com.liangsan.keyloler.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AnimatedProgressIndicator(visible: Boolean, modifier: Modifier = Modifier) {
    AnimatedVisibility(
        visible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically(),
        modifier = modifier
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        )
    }
}