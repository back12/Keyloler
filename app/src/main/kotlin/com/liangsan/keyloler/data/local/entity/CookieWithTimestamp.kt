package com.liangsan.keyloler.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.ktor.http.Cookie
import kotlinx.serialization.Serializable

@Serializable
@Entity
data class CookieWithTimestamp(
    @PrimaryKey
    val cookieName: String,
    val cookie: Cookie,
    val createdAt: Long
)