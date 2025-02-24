package com.liangsan.keyloler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForumDisplay(
    val forum: Forum,
    @SerialName("forum_threadlist")
    val threadList: List<Thread>
)
