package com.liangsan.keyloler.data.remote.serializer

import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlin.reflect.KClass

class KeylolResponseSerializer<T>(private val dataSerializer: KSerializer<T>) :
    JsonContentPolymorphicSerializer<KeylolResponse<T>>(KeylolResponse::class as KClass<KeylolResponse<T>>) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<KeylolResponse<T>> =
        when {
            "error" in element.jsonObject -> KeylolResponse.Error.serializer() as DeserializationStrategy<KeylolResponse<T>>
            "Variables" in element.jsonObject -> KeylolResponse.Success.serializer(dataSerializer)
            else -> throw SerializationException()
        }
}