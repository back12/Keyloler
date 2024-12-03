package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(MEMBER_URL)
class WebLoginVerify(
    val auth: String,
    val mod: String = "logging",
    val action: String = "login",
)