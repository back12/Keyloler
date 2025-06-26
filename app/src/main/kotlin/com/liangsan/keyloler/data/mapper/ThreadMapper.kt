package com.liangsan.keyloler.data.mapper

import com.liangsan.keyloler.data.local.entity.ThreadHistoryEntity
import com.liangsan.keyloler.domain.model.Thread
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Thread.toThreadHistoryEntity() = ThreadHistoryEntity(
    thread = this,
    createdTime = Clock.System.now().toEpochMilliseconds()
)

fun ThreadHistoryEntity.toDomain() = Thread(
    tid = thread.tid,
    readPerm = thread.readPerm,
    author = thread.author,
    authorId = thread.authorId,
    subject = thread.subject,
    dateline = thread.dateline,
    lastPost = thread.lastPost,
    lastPoster = thread.lastPoster,
    views = thread.views,
    replies = thread.replies,
    digest = thread.digest,
    attachment = thread.attachment,
    dbDateline = thread.dbDateline.toString(),
    dbLastPost = thread.dbLastPost,
)