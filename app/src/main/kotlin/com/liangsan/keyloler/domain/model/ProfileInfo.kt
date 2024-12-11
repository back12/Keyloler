package com.liangsan.keyloler.domain.model

import com.liangsan.keyloler.data.remote.dto.Group
import com.liangsan.keyloler.data.remote.dto.Medal

data class ProfileInfo(
    val uid: String,
    val username: String,
    val regDate: String,
    val credits: String,
    val healthPoints: String,
    val steamPoints: String,
    val powerPoints: String,
    val friends: String,
    val posts: String,
    val threads: String,
    val onlineTime: String,
    val medals: List<Medal>,
    val lastVisit: String,
    val lastActivity: String,
    val lastPost: String,
    val adminGroup: Group,
    val group: Group,
    val site: String
)
