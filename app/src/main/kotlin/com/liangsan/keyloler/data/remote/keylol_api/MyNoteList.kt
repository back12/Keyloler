package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
data class MyNoteList(
    val page: Int,
    override val version: Int = 4,
    override val module: String = "mynotelist"
) : DiscuzMobileApi()
