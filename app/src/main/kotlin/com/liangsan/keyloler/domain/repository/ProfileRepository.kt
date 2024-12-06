package com.liangsan.keyloler.domain.repository

interface ProfileRepository {

    suspend fun getUserNickname(uid: String): String?

    fun getUserAvatarUrl(uid: String): String
}