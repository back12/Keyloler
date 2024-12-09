package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.LoginResult
import com.liangsan.keyloler.domain.model.LoginParam
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface LoginRepository {

    suspend fun loginByPassword(username: String, password: String): Flow<LoginResult>

    suspend fun getSecureCodeUpdateParam(auth: String): Result<LoginParam>

    suspend fun getSecureCodeImageUrl(update: String, idHash: String): String

    suspend fun secureWebLogin(secCode: String, loginParam: LoginParam): Flow<LoginResult>
}