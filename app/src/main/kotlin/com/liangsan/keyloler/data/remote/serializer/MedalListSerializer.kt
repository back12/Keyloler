package com.liangsan.keyloler.data.remote.serializer

import com.liangsan.keyloler.data.remote.dto.Medal
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class MedalListSerializer : KSerializer<List<Medal>> {
    private val listSerializer = ListSerializer(Medal.serializer())
    override val descriptor: SerialDescriptor
        get() = listSerializer.descriptor

    override fun deserialize(decoder: Decoder): List<Medal> {
        val json = (decoder as JsonDecoder).decodeJsonElement()
        return try {
            json.jsonArray.map { Json.decodeFromJsonElement(Medal.serializer(), it) }
        } catch (e: IllegalArgumentException) {
            try {
                json.jsonObject.values.map { Json.decodeFromJsonElement(Medal.serializer(), it) }
            } catch (e: IllegalArgumentException) {
                emptyList()
            }
        }
    }

    override fun serialize(encoder: Encoder, value: List<Medal>) {
        listSerializer.serialize(encoder, value)
    }
}