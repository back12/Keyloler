package com.liangsan.keyloler.data.repository

import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.mapper.toDomain
import com.liangsan.keyloler.data.mapper.toEntity
import com.liangsan.keyloler.domain.model.SearchHistory
import com.liangsan.keyloler.domain.repository.SearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchHistoryRepositoryImpl(
    database: KeylolerDatabase
) : SearchHistoryRepository {

    private val dao = database.searchHistoryDao()

    override suspend fun addNewSearchHistory(searchHistory: SearchHistory) {
        dao.insertSearchHistory(searchHistory.toEntity())
    }

    override fun getNewestSearchHistory(limit: Int): Flow<List<SearchHistory>> {
        return dao.getNewestSearchHistory(limit).map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun deleteSearchHistory(searchHistory: SearchHistory) {
        dao.deleteSearchHistory(searchHistory.toEntity())
    }

    override suspend fun clearSearchHistory() {
        dao.clearSearchHistory()
    }

}