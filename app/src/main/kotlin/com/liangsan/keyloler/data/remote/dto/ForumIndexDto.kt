package com.liangsan.keyloler.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForumIndexDto(
    @SerialName("catlist")
    val category: List<Category>,
    @SerialName("forumlist")
    val forum: List<Forum>,
) {
    @Serializable
    data class Category(
        val fid: String,
        val name: String,
        val forums: List<String>
    )

    @Serializable
    data class Forum(
        val fid: String,
        val name: String,
        val threads: String,
        val posts: String,
        @SerialName("todayposts")
        val todayPosts: String,
        val description: String?,
        val icon: String?,
        @SerialName("sublist")
        val subList: List<Forum>?
    )
}
