package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.local.relation.ForumCategoryCrossRef
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
import kotlinx.coroutines.launch

class KeylolRepositoryImpl(
    database: KeylolerDatabase,
    private val networkService: KeylolerService
) : KeylolRepository {

    private val forumDao = database.forumDao()

    override fun fetchForumIndex(scope: CoroutineScope, refresh: Boolean): Flow<Result<Boolean>> =
        flow {
            emit(Result.Loading)

            if (!refresh) {
                if (forumDao.getCategoryCount() > 0) {
                    emit(Result.Success(false))
                    return@flow
                }
            }

            val response = networkService.getForumIndex()
            if (response is KeylolResponse.Error) {
                emit(Result.Error(response.error))
                return@flow
            }

            response as KeylolResponse.Success
            listOf(
                scope.async {
                    forumDao.clearForum()
                    val forumList = response.variables.forum.map { it.toEntity() }
                    forumDao.insertForum(forumList)
                },
                scope.async {
                    forumDao.clearForumCategory()
                    forumDao.clearForumCategoryCrossRef()
                    val categoryList = response.variables.category
                        .onEach { category ->
                            launch {
                                forumDao.insertForumCategoryCrossRef(category.forums.map { fid ->
                                    ForumCategoryCrossRef(category.fid.toInt(), fid.toInt())
                                })
                            }
                        }
                        .map { it.toEntity() }
                    forumDao.insertForumCategory(categoryList)
                }
            ).awaitAll()
            emit(Result.Success(true))
        }.flowOn(Dispatchers.IO)


    override fun getForumIndex(): Flow<ForumWithCategoryList> {
        return forumDao.getForumsWithCategory()
            .flowOn(Dispatchers.IO)
    }
}