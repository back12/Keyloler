package com.liangsan.keyloler.presentation.utils

import android.text.Html
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.Dp
import androidx.core.text.HtmlCompat

val LocalNavAnimatedVisibilityScope =
    staticCompositionLocalOf<AnimatedVisibilityScope> { throw IllegalStateException("No AnimatedVisibility found") }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    staticCompositionLocalOf<SharedTransitionScope> { throw IllegalStateException("No SharedElementScope found") }

val WindowInsets.Companion.Zero: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)

val NoCopyPasteClipboardManager = object : ClipboardManager {
    override fun getText(): AnnotatedString? = null

    override fun setText(annotatedString: AnnotatedString) = Unit

}

fun String.toHTMLAnnotatedString(): AnnotatedString =
    Html.fromHtml(this, HtmlCompat.FROM_HTML_MODE_COMPACT).toAnnotatedString()

@Composable
fun Int.toDp(): Dp {
    return with(LocalDensity.current) {
        this@toDp.toDp()
    }
}

@Composable
fun Dp.toPx(): Float {
    return with(LocalDensity.current) {
        this@toPx.toPx()
    }
}