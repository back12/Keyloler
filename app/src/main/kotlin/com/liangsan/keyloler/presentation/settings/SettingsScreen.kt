package com.liangsan.keyloler.presentation.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.liangsan.keyloler.R
import com.liangsan.keyloler.domain.model.AppSettings
import com.liangsan.keyloler.presentation.component.TopBar
import com.liangsan.keyloler.presentation.utils.isPresenting
import org.koin.androidx.compose.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

private val SettingsItemHeight = 64.dp

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    onNavigateUp: () -> Unit
) {
    with(viewModel.store) {
        val state by subscribe()
        state.isPresenting {
            SettingsScreenContent(
                modifier = modifier,
                appSettings = it,
                onSetIsDarkTheme = { intent(SettingsIntent.SetIsDarkTheme(it)) },
                onSetDynamicColor = { intent(SettingsIntent.SetDynamicColor(it)) },
                onNavigateUp = onNavigateUp
            )
        }
    }
}

@Composable
private fun SettingsScreenContent(
    modifier: Modifier = Modifier,
    appSettings: AppSettings,
    onSetIsDarkTheme: (Boolean) -> Unit,
    onSetDynamicColor: (Boolean) -> Unit,
    onNavigateUp: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(
                onNavigateUp = onNavigateUp
            ) {
                Text(
                    stringResource(R.string.setting),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .height(56.dp)
                        .wrapContentHeight()
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 12.dp),
        ) {
            item {
                SwitchSettingsItem(
                    title = "动态取色",
                    value = appSettings.dynamicColor,
                    onValueChange = onSetDynamicColor
                )
            }
        }
    }
}

@Composable
private fun SwitchSettingsItem(
    modifier: Modifier = Modifier,
    title: String,
    value: Boolean,
    enabled: Boolean = true,
    onValueChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(SettingsItemHeight),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Switch(
            checked = value,
            onCheckedChange = onValueChange,
            enabled = enabled
        )
    }
}