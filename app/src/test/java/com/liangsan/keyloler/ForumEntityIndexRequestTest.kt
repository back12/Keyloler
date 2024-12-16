package com.liangsan.keyloler

import com.liangsan.keyloler.data.remote.dto.ForumIndexDto
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.data.remote.keylol_api.ForumIndex
import com.liangsan.keyloler.data.remote.ktorHttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.cookies.AcceptAllCookiesStorage
import io.ktor.client.plugins.resources.get
import io.ktor.http.HttpStatusCode
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ForumEntityIndexRequestTest {

    private val httpClient = ktorHttpClient(AcceptAllCookiesStorage())

    @Test
    fun test() {
        runBlocking {
            val response = httpClient.get(ForumIndex())
            println(response.body<KeylolResponse<ForumIndexDto>>())
            assertEquals(HttpStatusCode.OK, response.status)
        }
    }
}