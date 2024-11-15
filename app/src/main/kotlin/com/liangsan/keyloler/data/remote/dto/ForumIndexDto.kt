package com.liangsan.keyloler.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForumIndexDto(
    @SerialName("catlist")
    val categoryList: List<CategoryList>,
    @SerialName("forumlist")
    val forumList: List<ForumList>,
) {
    @Serializable
    data class CategoryList(
        val fid: String,
        val name: String,
        val forums: List<String>
    )

    @Serializable
    data class ForumList(
        val fid: String,
        val name: String,
        val threads: String,
        val posts: String,
        @SerialName("todayposts")
        val todayPosts: String,
        val description: String?,
        val icon: String?,
        @SerialName("sublist")
        val subList: List<ForumList>?
    )
}
