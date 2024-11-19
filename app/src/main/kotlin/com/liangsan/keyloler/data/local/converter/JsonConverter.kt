package com.liangsan.keyloler.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface JsonConverter<T> {
    @TypeConverter
    fun parseToString(obj: T): String

    @TypeConverter
    fun parseFromJson(json: String): T
}

inline fun <reified T> defaultJsonConverter() = object : JsonConverter<T> {

    override fun parseToString(obj: T): String {
        return Json.encodeToString(obj)
    }

    override fun parseFromJson(json: String): T {
        return Json.decodeFromString(json)
    }
}