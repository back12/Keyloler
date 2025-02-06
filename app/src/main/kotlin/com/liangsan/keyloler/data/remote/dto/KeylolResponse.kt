package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.data.remote.serializer.KeylolResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import com.liangsan.keyloler.domain.utils.Result

@Serializable(with = KeylolResponseSerializer::class)
sealed interface KeylolResponse<out T> {

    @Serializable
    data class Success<out T>(
        @SerialName("Variables")
        val variables: T,
        @SerialName("Message")
        val message: Message?
    ) : KeylolResponse<T>

    @Serializable
    data class Error(
        val error: String
    ) : KeylolResponse<Nothing>
}

@Serializable
data class Message(
    @SerialName("messageval")
    val messageVal: String,
    @SerialName("messagestr")
    val messageStr: String
)

fun <T> kotlin.Result<KeylolResponse<T>>.mapToResult(): Result<T> {
    val response = getOrElse {
        return Result.Error(throwable = it)
    }

    if (response is KeylolResponse.Error) {
        return Result.Error(response.error)
    }

    response as KeylolResponse.Success
    return Result.Success(response.variables)
}

fun <T, R> kotlin.Result<KeylolResponse<T>>.mapToResult(transform: (T) -> R): Result<R> {
    val response = getOrElse {
        return Result.Error(throwable = it)
    }

    if (response is KeylolResponse.Error) {
        return Result.Error(response.error)
    }

    response as KeylolResponse.Success
    return Result.Success(transform(response.variables))
}