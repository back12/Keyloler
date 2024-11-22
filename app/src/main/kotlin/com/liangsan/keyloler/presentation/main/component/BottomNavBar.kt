package com.liangsan.keyloler.presentation.main.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.presentation.main.navigation.TopLevelDestination

val bottomBarHeight = 66.dp

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    visible: Boolean,
    destinations: List<TopLevelDestination> = TopLevelDestination.toList(),
    isSelected: (TopLevelDestination) -> Boolean,
    onNavigate: (TopLevelDestination) -> Unit
) {
    AnimatedVisibility(
        modifier = modifier,
        visible = visible,
        enter = fadeIn() + slideInVertically { it / 2 },
        exit = fadeOut() + slideOutVertically { it / 2 }
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = bottomBarHeight)
        ) {
            destinations.forEach {
                val label = stringResource(it.name)
                NavigationBarItem(
                    modifier = Modifier
                        .heightIn(min = bottomBarHeight)
                        .padding(top = 8.dp),
                    label = {
                        Text(text = label)
                    },
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = label,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    selected = isSelected(it),
                    onClick = {
                        onNavigate(it)
                    }
                )
            }
        }
    }
}