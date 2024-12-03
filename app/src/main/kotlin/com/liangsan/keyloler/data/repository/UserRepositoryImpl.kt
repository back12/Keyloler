package com.liangsan.keyloler.data.repository

import android.app.Application
import com.liangsan.keyloler.data.preferences.UserData
import com.liangsan.keyloler.data.preferences.userDataDataStore
import com.liangsan.keyloler.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(context: Application) : UserRepository {

    private val dataStore = context.userDataDataStore

    override fun getUserData(): Flow<UserData> {
        return dataStore.data
    }

    // Update every field of user data every time
    override suspend fun updateUserData(userData: UserData) {
        dataStore.updateData { userData }
    }
}