package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.remote.dto.ProfileDto
import com.liangsan.keyloler.domain.model.ProfileInfo

fun ProfileDto.toDomain() = ProfileInfo(
    uid = space.uid,
    username = space.username,
    regDate = space.regDate,
    credits = space.credits,
    healthPoints = space.healthPoints,
    steamPoints = space.steamPoints,
    powerPoints = space.powerPoints,
    friends = space.friends,
    posts = space.posts,
    threads = space.threads,
    onlineTime = space.onlineTime,
    medals = space.medals,
    adminGroup = space.adminGroup,
    group = space.group,
    site = space.site,
    lastVisit = space.lastVisit,
    lastActivity = space.lastActivity,
    lastPost = space.lastPost,
)