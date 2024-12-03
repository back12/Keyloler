package com.liangsan.keyloler.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object UserDataSerializer : Serializer<UserData> {
    override val defaultValue: UserData = UserData.getDefaultInstance()

    @Throws(InvalidProtocolBufferException::class)
    override suspend fun readFrom(input: InputStream): UserData {
        return UserData.parseFrom(input)
    }

    override suspend fun writeTo(t: UserData, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.userDataDataStore: DataStore<UserData> by dataStore(
    fileName = "userdata.pb",
    serializer = UserDataSerializer,
    corruptionHandler = ReplaceFileCorruptionHandler {
        UserData.getDefaultInstance()
    }
)