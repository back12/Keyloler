package com.liangsan.keyloler.domain.repository

import com.liangsan.keyloler.domain.model.SearchHistory
import kotlinx.coroutines.flow.Flow

interface SearchHistoryRepository {

    suspend fun addNewSearchHistory(searchHistory: SearchHistory)

    fun getNewestSearchHistory(limit: Int = 20): Flow<List<SearchHistory>>

    suspend fun deleteSearchHistory(searchHistory: SearchHistory)

    suspend fun clearSearchHistory()
}