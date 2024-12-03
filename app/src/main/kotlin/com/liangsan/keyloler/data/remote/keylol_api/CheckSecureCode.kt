package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(MISC_URL)
class CheckSecureCode(
    @SerialName("idhash")
    val idHash: String,
    @SerialName("secverify")
    val secVerify: String,
    val mod: String = "seccode",
    val action: String = "check",
    @SerialName("inajax")
    val inAjax: Int = 1,
)