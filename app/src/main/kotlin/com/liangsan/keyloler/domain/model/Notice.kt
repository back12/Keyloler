package com.liangsan.keyloler.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Notice(
    val author: String?,
    @SerialName("authorid")
    val authorId: String,
    val dateline: String,
    @SerialName("from_id")
    val fromId: String,
    @SerialName("from_idtype")
    val fromIdType: String,
    @SerialName("from_num")
    val fromNum: String,
    val id: String,
    val new: String,
    val note: String,
    @SerialName("notevar")
    val noteVar: NoteVar?,
    val type: String,
    val uid: String
) {
    @Serializable
    data class NoteVar(
        @SerialName("actoruid")
        val actorUid: String,
        @SerialName("actorusername")
        val actorUsername: String,
        val pid: String,
        val subject: String,
        val tid: String
    )
}