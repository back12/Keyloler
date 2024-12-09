package com.liangsan.keyloler.data.repository

import com.fleeksoft.ksoup.Ksoup
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.domain.model.LoginResult
import com.liangsan.keyloler.domain.repository.LoginRepository
import com.liangsan.keyloler.domain.model.LoginParam
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import kotlin.random.Random

class LoginRepositoryImpl(
    private val service: KeylolerService
) : LoginRepository {

    override suspend fun loginByPassword(username: String, password: String): Flow<LoginResult> =
        flow {
            emit(LoginResult.Loading)

            val response = service.apiLogin(username, password).getOrElse {
                emit(LoginResult.Error(throwable = it))
                return@flow
            }

            if (response is KeylolResponse.Error) {
                emit(LoginResult.Failed(LoginResult.Cause.Unknown(response.error)))
                return@flow
            }

            response as KeylolResponse.Success
            when (response.message?.messageVal) {
                "login_succeed" -> {
                    emit(
                        LoginResult.Success(
                            uid = response.variables.memberUid,
                            msg = response.message.messageStr
                        )
                    )
                }

                "login_seccheck2" -> {
                    response.variables.auth?.let {
                        emit(
                            LoginResult.NeedVerification(
                                auth = it,
                                msg = response.message.messageStr
                            )
                        )
                    } ?: run {
                        emit(LoginResult.Error(RuntimeException("验证码登录：auth值不存在")))
                    }
                }

                null -> {
                    emit(LoginResult.Failed(LoginResult.Cause.Unknown()))
                }

                else -> {
                    emit(LoginResult.Failed(LoginResult.Cause.WrongPassword))
                }
            }
        }

    override suspend fun getSecureCodeUpdateParam(auth: String): Result<LoginParam> {
        val html = service.webLoginVerify(auth).getOrElse {
            return Result.Error(error = "update值获取失败", throwable = it)
        }
        return try {
            val document = Ksoup.parse(html)
            val onclick =
                document.getElementsByClass("sec_button")[0].attribute("onclick")?.value!!
            val regex = "duceapp_updateseccode\\('([^']+)','([^']+)'\\)".toRegex()
            val groupValues = regex.find(onclick)?.groupValues!!
            val (_, loginHash, update) = groupValues
            val formHash = document.getElementsByAttributeValue(
                "name",
                "formhash"
            )[0].attribute("value")?.value!!
            val idHash = "S${Random.nextInt(1000)}"
            Result.Success(
                LoginParam(
                    auth = auth,
                    update = update,
                    loginHash = loginHash,
                    formHash = formHash,
                    idHash = idHash
                )
            )
        } catch (e: Exception) {
            when (e) {
                is NullPointerException,
                is IndexOutOfBoundsException -> {
                    Timber.e(e)
                    Result.Error(error = "update值获取失败", throwable = e)
                }

                else -> throw e
            }
        }
    }

    override suspend fun getSecureCodeImageUrl(update: String, idHash: String): String {
        return service.getSecureCodeImageUrl(update, idHash)
    }

    override suspend fun secureWebLogin(
        secCode: String,
        loginParam: LoginParam
    ): Flow<LoginResult> = flow {
        emit(LoginResult.Loading)

        val result = service.secureWebLogin(secCode, loginParam).getOrElse {
            emit(LoginResult.Error(throwable = it))
            return@flow
        }

        val secCodePassed = result.contains("succeed")

        if (secCodePassed) {
            val uid = try {
                val regex = "'uid':'(\\d+)'".toRegex()
                regex.find(result)?.groupValues!![1]
            } catch (e: NullPointerException) {
                emit(LoginResult.Failed(LoginResult.Cause.NoUidFound))
                return@flow
            }
            emit(LoginResult.Success(uid = uid, msg = "登陆成功"))
        } else {
            emit(LoginResult.Failed(LoginResult.Cause.WrongSecureCode))
        }
    }
}