package com.liangsan.keyloler.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = KeylolResponseSerializer::class)
sealed interface KeylolResponse<T> {

    @Serializable
    data class Success<T>(
        @SerialName("Variables")
        val variables: T
    ): KeylolResponse<T>

    @Serializable
    data class Error(
        val error: String
    ) : KeylolResponse<String>
}