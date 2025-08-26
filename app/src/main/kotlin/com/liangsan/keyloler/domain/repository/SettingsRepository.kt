package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.AppSettings
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getAppSettings(): Flow<AppSettings>

    suspend fun setIsDarkTheme(darkTheme: Boolean)

    suspend fun setDynamicColor(dynamicColor: Boolean)
}