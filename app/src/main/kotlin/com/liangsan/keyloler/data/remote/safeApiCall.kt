package com.liangsan.keyloler.data.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    execute: suspend () -> T
): Result<T> = withContext(Dispatchers.IO) {
    try {
        Result.success(execute())
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Result.failure(e)
    }
}