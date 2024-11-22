package com.liangsan.keyloler.domain.utils

/**
 * A generic class that holds a value with its loading status.
 *
 * @param D The return type of the [Result]
 * @param E The return type of the [Result] in case of business rule error
 */
sealed class Result<out D> {
    data class Success<out D>(val data: D) : Result<D>()
    data class Error(val error: String? = null, val throwable: Throwable? = null) :
        Result<Nothing>() {
        override fun toString(): String {
            return when {
                error != null -> error
                throwable != null -> throwable.toString()
                else -> "Error, no message."
            }
        }
    }

    data object Loading : Result<Nothing>()

    fun isSuccessful() = this is Success
    fun hasFailed() = this is Error
    fun isLoading() = this is Loading

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$error]"
            Loading -> "Loading"
        }
    }
}

/**
 * [Success.data][Result.Success.data] if [Result] is of type [Success][Result.Success]
 */
fun <D> Result<D>.successOr(fallback: D): D {
    return (this as? Result.Success<D>)?.data ?: fallback
}

inline fun <D> Result<D>.onSuccess(block: (D) -> Unit): Result<D> {
    if (this is Result.Success<D>) {
        block(data)
    }

    return this
}

inline fun <D> Result<D>.onError(block: (String?) -> Unit): Result<D> {
    if (this is Result.Error) {
        block(error)
    }

    return this
}

inline fun <D> Result<D>.whenFinished(block: () -> Unit): Result<D> {
    block()
    return this
}

val <D> Result<D>.data: D?
    get() = (this as? Result.Success)?.data