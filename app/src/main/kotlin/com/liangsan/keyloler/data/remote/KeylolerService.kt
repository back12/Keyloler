package com.liangsan.keyloler.data.remote

import com.fleeksoft.ksoup.Ksoup
import com.liangsan.keyloler.data.remote.dto.ForumIndexDto
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.data.remote.dto.LoginDto
import com.liangsan.keyloler.data.remote.keylol_api.Avatar
import com.liangsan.keyloler.data.remote.keylol_api.ForumIndex
import com.liangsan.keyloler.data.remote.keylol_api.Login
import com.liangsan.keyloler.data.remote.keylol_api.SecureCode
import com.liangsan.keyloler.data.remote.keylol_api.WebLogin
import com.liangsan.keyloler.data.remote.keylol_api.WebLoginVerify
import com.liangsan.keyloler.domain.model.LoginParam
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.href
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.parameters

class KeylolerService(private val httpClient: HttpClient) {

    suspend fun apiLogin(username: String, password: String): Result<KeylolResponse<LoginDto>> =
        safeApiCall {
            httpClient.submitForm(
                url = httpClient.href(Login()),
                formParameters = parameters {
                    append("username", username)
                    append("password", password)
                    append("loginfield", "auto")
                    append("loginsubmit", "yes")
                }
            ).body()
        }

    suspend fun webLoginVerify(auth: String): Result<String> =
        safeApiCall {
            httpClient.get(WebLoginVerify(auth = auth)).bodyAsText()
        }

    fun getSecureCodeImageUrl(update: String, idHash: String): String =
        "https://keylol.com${httpClient.href(SecureCode(update = update, idHash = idHash))}"

    suspend fun secureWebLogin(secCode: String, loginParam: LoginParam): Result<String> =
        safeApiCall {
            httpClient.submitForm(
                url = httpClient.href(WebLogin(loginParam.loginHash)),
                formParameters = parameters {
                    append("formhash", loginParam.formHash)
                    append("auth", loginParam.auth)
                    append("seccodehash", loginParam.idHash)
                    append("seccodeverify", secCode)
                    append("duceapp", "yes")
                    append("handlekey", "login")
                    append("cookietime", "2592000")
                }
            ).bodyAsText()
        }

    fun getUserAvatarUrl(uid: String): String =
        "https://keylol.com${httpClient.href(Avatar(uid = uid))}"

    suspend fun getUserNickname(uid: String): Result<String> =
        safeApiCall {
            val response = httpClient.get("https://keylol.com/suid-$uid").bodyAsText()
            Ksoup.parse(response).title().substringBefore("的个人资料", "")
        }

    suspend fun getForumIndex(): Result<KeylolResponse<ForumIndexDto>> =
        safeApiCall {
            httpClient.get(ForumIndex()).body()
        }
}