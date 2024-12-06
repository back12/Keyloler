package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource("/uc_server/avatar.php")
class Avatar(
    val uid: String
)