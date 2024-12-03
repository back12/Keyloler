package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource(INDEX_URL)
class Profile(
    val uid: String? = null,
    val username: String? = null,
    override val version: Int = 4,
    override val module: String = "profile"
) : DiscuzMobileApi() {
    init {
        require(uid != null || username != null) {
            "uid 和 username皆为空，需要至少一个参数。"
        }
    }
}