package com.liangsan.keyloler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Thread(
    val tid: String,
    @SerialName("readperm")
    val readPerm: String = "",
    val author: String,
    @SerialName("authorid")
    val authorId: String,
    val subject: String,
    val dateline: String,
    @SerialName("lastpost")
    val lastPost: String = "",
    @SerialName("lastposter")
    val lastPoster: String = "",
    val views: String = "",
    val replies: String = "",
    val digest: String = "",
    val attachment: String = "",
    @SerialName("dbdateline")
    val dbDateline: String = "",
    @SerialName("dblastpost")
    val dbLastPost: String = "",
    val forum: String? = null
)