package com.liangsan.keyloler.presentation.utils

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.staticCompositionLocalOf

val LocalNavAnimatedVisibilityScope =
    staticCompositionLocalOf<AnimatedVisibilityScope> { throw IllegalStateException("No AnimatedVisibility found") }

@OptIn(ExperimentalSharedTransitionApi::class)
val LocalSharedTransitionScope =
    staticCompositionLocalOf<SharedTransitionScope> { throw IllegalStateException("No SharedElementScope found") }

val WindowInsets.Companion.Zero: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)