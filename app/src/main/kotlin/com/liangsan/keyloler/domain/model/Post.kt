package com.liangsan.keyloler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val pid: String,
    val tid: String,
    val first: String,
    val author: String,
    @SerialName("authorid")
    val authorId: String,
    val dateline: String,
    val message: String,
    val username: String,
    val number: Int
)