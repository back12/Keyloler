package com.liangsan.keyloler.presentation.settings

import pro.respawn.flowmvi.api.MVIIntent

sealed interface SettingsIntent: MVIIntent {

    data class SetIsDarkTheme(val isDarkTheme: Boolean) : SettingsIntent

    data class SetDynamicColor(val dynamicColor: Boolean) : SettingsIntent
}