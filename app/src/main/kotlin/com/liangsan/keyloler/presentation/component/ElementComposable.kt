package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import coil3.compose.AsyncImage

sealed interface ElementComposable {

    // https://issuetracker.google.com/issues/317490247
    @Composable
    operator fun invoke(modifier: Modifier)
}

@Immutable
data class TextElement(val content: AnnotatedString) : ElementComposable {
    @Composable
    override fun invoke(modifier: Modifier) {
        Text(content, modifier = modifier)
    }
}

@Immutable
data class ImageElement(val src: String?) : ElementComposable {
    @Composable
    override fun invoke(modifier: Modifier) {
        AsyncImage(
            modifier = modifier,
            model = src,
            contentDescription = null
        )
    }
}

@Immutable
data object NewLineElement : ElementComposable {
    @Composable
    override fun invoke(modifier: Modifier) {
        Spacer(modifier = modifier.fillMaxWidth())
    }
}