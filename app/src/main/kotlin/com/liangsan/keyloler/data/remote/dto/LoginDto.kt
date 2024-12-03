package com.liangsan.keyloler.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val auth: String?,
    @SerialName("cookiepre")
    val cookiePre: String,
    @SerialName("formhash")
    val formHash: String,
    @SerialName("groupid")
    val groupId: String,
    @SerialName("ismoderator")
    val isModerator: String?,
    val loginUrl: String?,
    @SerialName("member_avatar")
    val memberAvatar: String,
    @SerialName("member_uid")
    val memberUid: String,
    @SerialName("member_username")
    val memberUsername: String,
    val notice: Notice,
    @SerialName("readaccess")
    val readAccess: String,
    @SerialName("saltkey")
    val saltKey: String
) {
    @Serializable
    data class Notice(
        @SerialName("newmypost")
        val newMyPost: String,
        @SerialName("newpm")
        val newPm: String,
        @SerialName("newprompt")
        val newPrompt: String,
        @SerialName("newpush")
        val newPush: String
    )
}