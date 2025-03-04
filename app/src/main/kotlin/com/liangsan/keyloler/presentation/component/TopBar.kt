package com.liangsan.keyloler.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R

@Composable
inline fun TopBar(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surfaceContainerHighest,
    noinline onNavigateUp: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .statusBarsPadding()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onNavigateUp
        ) {
            Icon(
                painter = painterResource(R.drawable.round_arrow_back_24),
                contentDescription = null
            )
        }
        content()
    }
}