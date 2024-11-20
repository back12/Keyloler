package com.liangsan.keyloler.di

import androidx.room.Room
import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.ktorHttpClient
import com.liangsan.keyloler.data.repository.ForumCategoryRepositoryImpl
import com.liangsan.keyloler.data.repository.SearchHistoryRepositoryImpl
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import com.liangsan.keyloler.domain.repository.SearchHistoryRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single {
        Room.databaseBuilder(
            get(),
            KeylolerDatabase::class.java,
            "KeylolerDatabase.db"
        )
            .build()
    }

    single {
        ktorHttpClient
    }

    singleOf(::KeylolerService)

    singleOf(::ForumCategoryRepositoryImpl).bind<ForumCategoryRepository>()

    singleOf(::SearchHistoryRepositoryImpl).bind<SearchHistoryRepository>()
}