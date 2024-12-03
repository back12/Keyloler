package com.liangsan.keyloler.data.remote

import com.liangsan.keyloler.data.remote.dto.ForumIndexDto
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.data.remote.dto.LoginDto
import com.liangsan.keyloler.data.remote.keylol_api.CheckSecureCode
import com.liangsan.keyloler.data.remote.keylol_api.ForumIndex
import com.liangsan.keyloler.data.remote.keylol_api.Login
import com.liangsan.keyloler.data.remote.keylol_api.SecureCode
import com.liangsan.keyloler.data.remote.keylol_api.WebLogin
import com.liangsan.keyloler.data.remote.keylol_api.WebLoginVerify
import com.liangsan.keyloler.domain.utils.LoginParam
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.plugins.resources.href
import io.ktor.client.request.forms.submitForm
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

    suspend fun checkSecureCode(idHash: String, secCode: String): Result<Boolean> =
        safeApiCall {
            httpClient.get(CheckSecureCode(idHash = idHash, secVerify = secCode))
                .bodyAsText().contains("succeed")
        }

    suspend fun secureWebLogin(secCode: String, loginParam: LoginParam): Result<Boolean> =
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
            ).bodyAsText().contains("succeed")
        }

    suspend fun getForumIndex(): Result<KeylolResponse<ForumIndexDto>> =
        safeApiCall {
            httpClient.get(ForumIndex()).body()
        }
}