package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.ProfileInfo
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    suspend fun getUserNickname(uid: String): String?

    fun getUserAvatarUrl(uid: String): String

    fun getProfileInfo(uid: String): Flow<Result<ProfileInfo>>
}