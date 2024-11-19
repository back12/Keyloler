package com.liangsan.keyloler.presentation.utils

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun getString(@StringRes resId: Int): String {
    val context = LocalContext.current
    return context.getString(resId)
}

val WindowInsets.Companion.Zero: WindowInsets
    get() = WindowInsets(0, 0, 0, 0)