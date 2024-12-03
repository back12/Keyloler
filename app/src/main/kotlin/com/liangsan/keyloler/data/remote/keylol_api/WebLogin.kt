package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource
import kotlinx.serialization.SerialName

@Resource(MEMBER_URL)
class WebLogin(
    @SerialName("loginhash")
    val loginHash: String,
    val mod: String = "logging",
    val action: String = "login",
    @SerialName("loginsubmit")
    val loginSubmit: String = "yes",
    @SerialName("inajax")
    val inAjax: Int = 1
)