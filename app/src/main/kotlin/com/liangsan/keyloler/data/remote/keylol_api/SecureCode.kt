package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(MISC_URL)
class SecureCode(
    val update: String,
    @SerialName("idhash")
    val idHash: String,
    val mod: String = "seccode",
)