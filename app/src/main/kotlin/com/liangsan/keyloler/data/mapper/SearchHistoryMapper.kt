package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.local.entity.SearchHistoryEntity
import com.liangsan.keyloler.domain.model.SearchHistory
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun SearchHistoryEntity.toDomain() = SearchHistory(
    id = id,
    content = content,
    createTime = Instant.fromEpochMilliseconds(createTime)
        .toLocalDateTime(TimeZone.currentSystemDefault())
)

fun SearchHistory.toEntity() = SearchHistoryEntity(
    id = id,
    content = content,
    createTime = createTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
)