package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.mapper.toEntity
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.dto.KeylolResponse
import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import com.liangsan.keyloler.domain.repository.KeylolRepository
import com.liangsan.keyloler.domain.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class KeylolRepositoryImpl(
    database: KeylolerDatabase,
    private val networkService: KeylolerService
) : KeylolRepository {

    private val forumDao = database.forumDao()

    override fun CoroutineScope.fetchForumIndex(): Flow<Result<Boolean>> = flow {
        withContext(Dispatchers.IO) {
            emit(Result.Loading)

            val response = networkService.getForumIndex()
            if (response is KeylolResponse.Error) {
                emit(Result.Error(response.error))
                return@withContext
            }

            response as KeylolResponse.Success
            listOf(
                async {
                    forumDao.clearAllForum()
                    val forumList = response.variables.forum.map { it.toEntity() }
                    forumDao.insertForum(forumList)
                },
                async {
                    forumDao.clearAllForumCategory()
                    val categoryList = response.variables.category.map { it.toEntity() }
                    forumDao.insertForumCategory(categoryList)
                }
            ).awaitAll()
            emit(Result.Success(true))
        }
    }

    override fun getForumIndex(): Flow<ForumWithCategoryList> {
        return forumDao.getForumsWithCategory()
            .flowOn(Dispatchers.IO)
    }
}