package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
class GetThread(
    val tid: String,
    val page: Int,
    val cp: String = "all",
    override val version: Int = 4,
    override val module: String = "viewthread"
) : DiscuzMobileApi()