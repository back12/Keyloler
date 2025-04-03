package com.liangsan.keyloler.data.remote.dto

import com.liangsan.keyloler.domain.model.Notice
import kotlinx.serialization.Serializable

@Serializable
data class MyNoteDto(
    val list: List<Notice>
)
