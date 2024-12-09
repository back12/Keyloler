package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import kotlinx.coroutines.flow.Flow

interface CookiesRepository {

    fun getCookieWithTimestampsFlow(): Flow<List<CookieWithTimestamp>>

    suspend fun getCookieWithTimestamps(): List<CookieWithTimestamp>

    suspend fun addCookie(cookieWithTimestamp: CookieWithTimestamp)

    suspend fun deleteCookie(cookieWithTimestamp: CookieWithTimestamp)
}