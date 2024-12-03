package com.liangsan.keyloler.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import timber.log.Timber

suspend fun <T> safeApiCall(
    execute: suspend () -> T
): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(execute())
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Timber.e(e)
        Result.failure(e)
    }
}