package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
class ForumIndex(
    val version: Int = 4,
    val module: String = "forumindex"
)