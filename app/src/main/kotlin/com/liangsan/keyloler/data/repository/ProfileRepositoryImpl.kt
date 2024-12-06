package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.domain.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val service: KeylolerService
): ProfileRepository {

    override suspend fun getUserNickname(uid: String): String? {
        return service.getUserNickname(uid).getOrNull()
    }

    override fun getUserAvatarUrl(uid: String): String {
        return service.getUserAvatarUrl(uid)
    }


}