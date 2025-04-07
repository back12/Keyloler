package com.liangsan.keyloler.presentation.thread

sealed interface ThreadScreenEvent {
    data class JumpToPost(val index: Int) : ThreadScreenEvent
}