package com.liangsan.keyloler

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json {
    prettyPrint = true
}

inline fun <reified T> prettyPrintln(message: T?) {
    println(json.encodeToString(message))
}