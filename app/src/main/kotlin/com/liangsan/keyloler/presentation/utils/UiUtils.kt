package com.liangsan.keyloler.presentation.utils

import android.text.Html
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.NativeClipboard
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

@Composable
fun noCopyPasteClipboardManager(): Clipboard {
    val clipboard = LocalClipboard.current
    return remember {
        object : Clipboard {
            override val nativeClipboard: NativeClipboard
                get() = clipboard.nativeClipboard

            override suspend fun getClipEntry(): ClipEntry? = null

            override suspend fun setClipEntry(clipEntry: ClipEntry?) = Unit
        }
    }
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