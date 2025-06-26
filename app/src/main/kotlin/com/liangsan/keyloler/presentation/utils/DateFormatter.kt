package com.liangsan.keyloler.presentation.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(FormatStringsInDatetimeFormats::class, ExperimentalTime::class)
fun Long.format(pattern: String = "yyyy-MM-dd HH:mm"): String {
    val datetime =
        Instant.fromEpochMilliseconds(this).toLocalDateTime(TimeZone.currentSystemDefault())
    val format = LocalDateTime.Format {
        byUnicodePattern(pattern)
    }
    return datetime.format(format)
}