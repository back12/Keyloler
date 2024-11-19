package com.liangsan.keyloler.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import com.liangsan.keyloler.presentation.main.component.bottomBarHeight

fun Modifier.bottomBarPadding() = this.padding(bottom = bottomBarHeight)

fun Modifier.onTap(onClick: () -> Unit) =
    this.clickable(
        interactionSource = null,
        indication = null,
        onClick = onClick
    )