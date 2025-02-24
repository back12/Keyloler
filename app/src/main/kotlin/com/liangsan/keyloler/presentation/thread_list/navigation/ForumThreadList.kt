package com.liangsan.keyloler.presentation.thread_list.navigation

import com.liangsan.keyloler.domain.model.Forum
import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

@Serializable
data class ForumThreadList(
    val forum: Forum
) : KeylolerDestination(false)

