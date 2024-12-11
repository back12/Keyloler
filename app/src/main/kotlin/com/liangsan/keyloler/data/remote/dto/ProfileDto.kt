package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.data.remote.serializer.MedalListSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileDto(
    val space: Space
) {
    @Serializable
    data class Space(
        val uid: String,
        val username: String,
        @SerialName("regdate")
        val regDate: String,
        val credits: String,        // 积分
        @SerialName("extcredits1")
        val healthPoints: String,   // 体力
        @SerialName("extcredits3")
        val steamPoints: String,    // 蒸汽
        @SerialName("extcredits4")
        val powerPoints: String,    // 动力
        val friends: String,
        val posts: String,
        val threads: String,
        @SerialName("oltime")
        val onlineTime: String,
        @Serializable(with = MedalListSerializer::class)
        val medals: List<Medal>,
        @SerialName("lastvisit")
        val lastVisit: String,
        @SerialName("lastactivity")
        val lastActivity: String,
        @SerialName("lastpost")
        val lastPost: String,
        @SerialName("admingroup")
        val adminGroup: Group,
        val group: Group,
        val site: String
    )
}

@Serializable
data class Medal(
    val name: String,
    val image: String,
    val description: String,
    @SerialName("medalid")
    val medalId: String
)

@Serializable
data class Group(
    val type: String?,
    @SerialName("grouptitle")
    val groupTitle: String?,
    val icon: String,
    @SerialName("readaccess")
    val readAccess: String?
)