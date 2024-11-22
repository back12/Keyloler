package com.liangsan.keyloler.presentation.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

val LocalSnackbarScope = staticCompositionLocalOf<SnackbarScope> { throw RuntimeException() }

@Immutable
class SnackbarScope(
    private val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {
    private var job: Job? = null

    fun launch(block: suspend SnackbarHostState.() -> Unit) {
        // Cancel suspending showSnackbar function if last snackbar is still presenting
        job?.cancel()
        job = scope.launch {
            snackbarHostState.block()
            job = null
        }
    }
}