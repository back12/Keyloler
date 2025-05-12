package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import com.liangsan.keyloler.domain.repository.CookiesRepository
import kotlinx.coroutines.flow.Flow

class CookiesRepositoryImpl(db: KeylolerDatabase) : CookiesRepository {
    private val dao = db.cookieWithTimestampDao()

    override fun getCookieWithTimestampsFlow(): Flow<List<CookieWithTimestamp>> {
        return dao.getAllCookieWithTimeStampFlow()
    }

    override suspend fun getCookieWithTimestamps(): List<CookieWithTimestamp> =
        dao.getAllCookieWithTimeStamp()


    override suspend fun addCookie(cookieWithTimestamp: CookieWithTimestamp) {
        dao.insertCookieWithTimestamp(cookieWithTimestamp)
    }

    override suspend fun deleteCookie(cookieWithTimestamp: CookieWithTimestamp) {
        dao.deleteCookieWithTimeStamp(cookieWithTimestamp)
    }

    override suspend fun clearAllCookies() {
        dao.clearAllCookies()
    }
}