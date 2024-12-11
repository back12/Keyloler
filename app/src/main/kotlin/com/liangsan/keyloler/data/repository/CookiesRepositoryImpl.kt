package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import com.liangsan.keyloler.domain.repository.CookiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class CookiesRepositoryImpl(db: KeylolerDatabase) : CookiesRepository {
    private val dao = db.cookieWithTimestampDao()

    override fun getCookieWithTimestampsFlow(): Flow<List<CookieWithTimestamp>> {
        return dao.getAllCookieWithTimeStampFlow().flowOn(Dispatchers.IO)
    }

    override suspend fun getCookieWithTimestamps(): List<CookieWithTimestamp> =
        withContext(Dispatchers.IO) {
            return@withContext dao.getAllCookieWithTimeStamp()
        }

    override suspend fun addCookie(cookieWithTimestamp: CookieWithTimestamp) {
        withContext(Dispatchers.IO) {
            dao.insertCookieWithTimestamp(cookieWithTimestamp)
        }
    }

    override suspend fun deleteCookie(cookieWithTimestamp: CookieWithTimestamp) {
        withContext(Dispatchers.IO) {
            dao.deleteCookieWithTimeStamp(cookieWithTimestamp)
        }
    }

    override suspend fun clearAllCookies() {
        withContext(Dispatchers.IO) {
            dao.clearAllCookies()
        }
    }
}