package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.domain.model.Thread
import kotlinx.serialization.Serializable

@Serializable
data class NewThreadDto(
    val data: List<Thread>
)
