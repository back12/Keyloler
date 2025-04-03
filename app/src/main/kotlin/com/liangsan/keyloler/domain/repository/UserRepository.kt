package com.liangsan.keyloler.domain.repository

import androidx.paging.PagingData
import com.liangsan.keyloler.data.preferences.UserData
import com.liangsan.keyloler.domain.model.Notice
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserData(): Flow<UserData>

    suspend fun updateUserData(block: suspend (userData: UserData) -> UserData)

    suspend fun logout()

    fun getMyNoteList(): Flow<PagingData<Notice>>
}