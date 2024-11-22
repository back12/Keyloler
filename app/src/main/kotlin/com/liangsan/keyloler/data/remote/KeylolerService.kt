package com.liangsan.keyloler.data.remote

import com.liangsan.keyloler.data.remote.dto.ForumIndexDto
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.data.remote.keylol_api.ForumIndex
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class KeylolerService(private val httpClient: HttpClient) {

    suspend fun getForumIndex(): Result<KeylolResponse<ForumIndexDto>> =
        safeApiCall {
            httpClient.get(ForumIndex()).body()
        }
}