package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.remote.dto.ForumIndexDto

@Throws(NumberFormatException::class)
fun ForumIndexDto.Category.toEntity() = ForumCategoryEntity(
    fcid = fid.toInt(),
    name = name
)

@Throws(NumberFormatException::class)
fun ForumIndexDto.Forum.toEntity(): ForumEntity = ForumEntity(
    fid = fid.toInt(),
    name = name,
    threads = threads.toInt(),
    posts = posts.toInt(),
    todayPosts = todayPosts.toInt(),
    description = description,
    icon = icon,
    subList = subList?.map { it.toEntity() }
)