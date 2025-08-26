package com.liangsan.keyloler.presentation.utils

import pro.respawn.flowmvi.api.MVIState

sealed interface UIState<out T> : MVIState {

    data object Loading : UIState<Nothing>

    data class Presenting<T>(
        val state: T
    ) : UIState<T>
}

inline fun <T> UIState<T>.isPresenting(presenting: (T) -> Unit) {
    (this as? UIState.Presenting)?.let {
        presenting(it.state)
    }
}