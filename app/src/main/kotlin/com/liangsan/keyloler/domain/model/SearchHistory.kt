package com.liangsan.keyloler.domain.model

import kotlinx.datetime.LocalDateTime

data class SearchHistory(
    val content: String,
    val createTime: LocalDateTime,
    val id: Int? = null,
)
