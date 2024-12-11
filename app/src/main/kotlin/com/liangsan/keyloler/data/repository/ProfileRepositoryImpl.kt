package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.mapper.toDomain
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.domain.model.ProfileInfo
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl(
    private val service: KeylolerService
) : ProfileRepository {

    override suspend fun getUserNickname(uid: String): String? {
        return service.getUserNickname(uid).getOrNull()
    }

    override fun getUserAvatarUrl(uid: String): String {
        return service.getUserAvatarUrl(uid)
    }

    override fun getProfileInfo(uid: String): Flow<Result<ProfileInfo>> = flow {
        emit(Result.Loading)

        val response = service.getProfileInfo(uid = uid).getOrElse {
            emit(Result.Error(throwable = it))
            return@flow
        }

        if (response is KeylolResponse.Error) {
            emit(Result.Error(response.error))
            return@flow
        }

        response as KeylolResponse.Success
        emit(Result.Success(response.variables.toDomain()))
    }

}