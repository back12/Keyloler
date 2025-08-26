package com.liangsan.keyloler.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object AppDataSerializer : Serializer<AppData> {
    override val defaultValue: AppData = AppData.getDefaultInstance()

    @Throws(InvalidProtocolBufferException::class)
    override suspend fun readFrom(input: InputStream): AppData {
        return AppData.parseFrom(input)
    }

    override suspend fun writeTo(t: AppData, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.dataStore: DataStore<AppData> by dataStore(
    fileName = "appdata.pb",
    serializer = AppDataSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler {
        AppData.getDefaultInstance()
    }
)