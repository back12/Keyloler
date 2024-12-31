package com.liangsan.keyloler.presentation.thread.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

@Serializable
data class Thread(
    val tid: String,
    val title: String
) : KeylolerDestination(false)