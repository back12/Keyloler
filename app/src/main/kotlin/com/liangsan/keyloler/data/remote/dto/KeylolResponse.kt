package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.data.remote.serializer.KeylolResponseSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = KeylolResponseSerializer::class)
sealed interface KeylolResponse<out T> {

    @Serializable
    data class Success<out T>(
        @SerialName("Variables")
        val variables: T
    ) : KeylolResponse<T>

    @Serializable
    data class Error(
        val error: String
    ) : KeylolResponse<Nothing>
}