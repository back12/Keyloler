package com.liangsan.keyloler.domain.repository

import androidx.paging.PagingData
import com.liangsan.keyloler.data.preferences.AppData
import com.liangsan.keyloler.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserData(): Flow<AppData>

    suspend fun updateUserData(block: suspend (userData: AppData) -> AppData)

    suspend fun logout()

    fun getMyNoteList(): Flow<PagingData<Notice>>
}