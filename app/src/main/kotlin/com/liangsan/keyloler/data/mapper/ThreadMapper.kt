package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.local.entity.NewThreadEntity
import com.liangsan.keyloler.domain.model.Thread

fun Thread.toNewThreadEntity() = NewThreadEntity(
    tid = tid,
    readPerm = readPerm,
    author = author,
    authorId = authorId,
    subject = subject,
    dateline = dateline,
    lastPost = lastPost,
    lastPoster = lastPoster,
    views = views,
    replies = replies,
    digest = digest,
    attachment = attachment,
    dbDateline = dbDateline.toLong(),
    dbLastPost = dbLastPost,
)

fun NewThreadEntity.toDomain() = Thread(
    tid = tid,
    readPerm = readPerm,
    author = author,
    authorId = authorId,
    subject = subject,
    dateline = dateline,
    lastPost = lastPost,
    lastPoster = lastPoster,
    views = views,
    replies = replies,
    digest = digest,
    attachment = attachment,
    dbDateline = dbDateline.toString(),
    dbLastPost = dbLastPost,
)