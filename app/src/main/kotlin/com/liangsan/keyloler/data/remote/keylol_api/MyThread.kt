package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
data class MyThread(
    val page: Int,
    override val version: Int = 4,
    override val module: String = "mythread"
) : DiscuzMobileApi()
