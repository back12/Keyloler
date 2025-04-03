package com.liangsan.keyloler.data.repository

import android.app.Application
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.liangsan.keyloler.data.preferences.UserData
import com.liangsan.keyloler.data.preferences.userDataDataStore
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.data_source.MyNotePagingSource
import com.liangsan.keyloler.domain.model.Notice
import com.liangsan.keyloler.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl(
    context: Application,
    private val service: KeylolerService
) : UserRepository {

    private val dataStore = context.userDataDataStore

    override fun getUserData(): Flow<UserData> {
        return dataStore.data
    }

    override suspend fun updateUserData(block: suspend (userData: UserData) -> UserData) {
        dataStore.updateData(block)
    }

    override suspend fun logout() {
        dataStore.updateData {
            it.toBuilder().clear().build()
        }
    }

    override fun getMyNoteList(): Flow<PagingData<Notice>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = {
                MyNotePagingSource(service)
            }
        )
        return pager.flow
    }
}