package com.liangsan.keyloler.presentation.thread.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

/**
 * @property tid        thread id
 * @property title      thread title
 * @property pid        optional post id, for jumping to certain post
 */
@Serializable
data class ViewThread(
    val tid: String,
    val title: String,
    val pid: String? = null
) : KeylolerDestination(false)