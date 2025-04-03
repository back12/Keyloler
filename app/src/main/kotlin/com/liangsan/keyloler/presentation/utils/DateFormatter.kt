package com.liangsan.keyloler.presentation.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

@OptIn(FormatStringsInDatetimeFormats::class)
fun Long.format(pattern: String = "yyyy-MM-dd HH:mm"): String {
    val datetime =
        Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val format = LocalDateTime.Format {
        byUnicodePattern(pattern)
    }
    return datetime.format(format)
}