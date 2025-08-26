package com.liangsan.keyloler.data.repository

import android.app.Application
import com.liangsan.keyloler.data.preferences.dataStore
import com.liangsan.keyloler.domain.model.AppSettings
import com.liangsan.keyloler.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    context: Application
) : SettingsRepository {
    private val dataStore = context.dataStore

    override fun getAppSettings(): Flow<AppSettings> {
        return dataStore.data.map {
            AppSettings(
                isDarkTheme = it.isDarkTheme,
                dynamicColor = it.dynamicColor
            )
        }.distinctUntilChanged()
    }

    override suspend fun setIsDarkTheme(darkTheme: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsDarkTheme(darkTheme)
                .build()
        }
    }

    override suspend fun setDynamicColor(dynamicColor: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setDynamicColor(dynamicColor)
                .build()
        }
    }

}