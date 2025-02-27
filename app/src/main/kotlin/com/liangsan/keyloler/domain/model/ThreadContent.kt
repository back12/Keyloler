package com.liangsan.keyloler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreadContent(
    val thread: Thread,
    @SerialName("postlist")
    val postList: List<Post>
)