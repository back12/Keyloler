package com.liangsan.keyloler.data.remote.keylol_api

import io.ktor.resources.Resource

@Resource("/forum.php")
class Guide private constructor(
    val mod: String = "guide",
    val view: String
) {
    companion object {
        // 最新热门
        val Hot = Guide(view = "hot")

        // 最新精华
        val Digest = Guide(view = "digest")

        // 最新回复
        val New = Guide(view = "new")

        // 最新发表
        val NewThread = Guide(view = "newThread")

        // 抢沙发
        val Sofa = Guide(view = "sofa")
    }
}