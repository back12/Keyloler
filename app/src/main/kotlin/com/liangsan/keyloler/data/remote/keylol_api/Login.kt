package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
class Login(
    override val version: Int = 4,
    override val module: String = "login"
) : DiscuzMobileApi()