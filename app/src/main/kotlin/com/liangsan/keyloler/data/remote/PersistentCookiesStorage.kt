package com.liangsan.keyloler.data.remote

import com.liangsan.keyloler.data.local.entity.CookieWithTimestamp
import com.liangsan.keyloler.domain.repository.CookiesRepository
import io.ktor.client.plugins.cookies.CookiesStorage
import io.ktor.client.plugins.cookies.fillDefaults
import io.ktor.http.Cookie
import io.ktor.http.Url
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class PersistentCookiesStorage(
    private val cookiesRepository: CookiesRepository
) : CookiesStorage {

    private val mutex = Mutex()
    private val scope = CoroutineScope(Job())

    override suspend fun get(requestUrl: Url): List<Cookie> = mutex.withLock {
        val now = clock()

        val cookies = cookiesRepository.getCookieWithTimestamps()

        cookies.asSequence().filter {
            val expires = it.cookie.maxAgeOrExpires(it.createdAt) ?: return@filter true
            val expired = now > expires
            if (expired) {
                scope.launch {
                    cookiesRepository.deleteCookie(it)
                }
            }
            !expired
        }.map { it.cookie }.toList()
    }

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie) {
        with(cookie) {
            if (name.isBlank()) return
        }

        mutex.withLock {
            val createdAt = clock()
            val cookieWithTimestamp = CookieWithTimestamp(
                cookieName = cookie.name,
                cookie = cookie.fillDefaults(requestUrl),
                createdAt = createdAt
            )
            cookiesRepository.addCookie(cookieWithTimestamp)
        }
    }

    override fun close() {
        scope.cancel()
    }

    private fun Cookie.maxAgeOrExpires(createdAt: Long): Long? =
        maxAge?.let { createdAt + it * 1000L } ?: expires?.timestamp

    private fun clock() = getTimeMillis()
}