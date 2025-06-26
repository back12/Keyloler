package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.local.entity.SearchHistoryEntity
import com.liangsan.keyloler.domain.model.SearchHistory
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun SearchHistoryEntity.toDomain() = SearchHistory(
    id = id,
    content = content,
    createTime = Instant.fromEpochMilliseconds(createTime)
        .toLocalDateTime(TimeZone.currentSystemDefault())
)

@OptIn(ExperimentalTime::class)
fun SearchHistory.toEntity() = SearchHistoryEntity(
    id = id,
    content = content,
    createTime = createTime.toInstant(TimeZone.currentSystemDefault()).toEpochMilliseconds()
)