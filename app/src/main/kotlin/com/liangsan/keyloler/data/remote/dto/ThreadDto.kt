package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.domain.model.Post
import com.liangsan.keyloler.domain.model.Thread
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ThreadDto(
    val thread: Thread,
    @SerialName("postlist")
    val postList: List<Post>
)
