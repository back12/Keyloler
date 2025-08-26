package com.liangsan.keyloler.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liangsan.keyloler.domain.model.AppSettings
import com.liangsan.keyloler.domain.repository.SettingsRepository
import com.liangsan.keyloler.presentation.utils.UIState
import kotlinx.coroutines.flow.collectLatest
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce
import pro.respawn.flowmvi.plugins.whileSubscribed

class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel(), Container<UIState<AppSettings>, SettingsIntent, Nothing> {

    override val store by lazyStore(
        initial = UIState.Loading,
        scope = viewModelScope
    ) {
        whileSubscribed {
            settingsRepository.getAppSettings().collectLatest { appSettings ->
                updateState {
                    UIState.Presenting(appSettings)
                }
            }
        }

        reduce {
            when (it) {
                is SettingsIntent.SetDynamicColor -> {
                    settingsRepository.setDynamicColor(it.dynamicColor)
                }

                is SettingsIntent.SetIsDarkTheme -> {
                    settingsRepository.setIsDarkTheme(it.isDarkTheme)
                }
            }
        }
    }
}