package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.data.preferences.UserData
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getUserData(): Flow<UserData>

    suspend fun updateUserData(block: suspend (userData: UserData) -> UserData)

    suspend fun logout()
}