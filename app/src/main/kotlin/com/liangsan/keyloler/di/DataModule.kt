package com.liangsan.keyloler.di

import androidx.room.Room
import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.PersistentCookiesStorage
import com.liangsan.keyloler.data.remote.ktorHttpClient
import com.liangsan.keyloler.data.repository.CookiesRepositoryImpl
import com.liangsan.keyloler.data.repository.ForumCategoryRepositoryImpl
import com.liangsan.keyloler.data.repository.IndexRepositoryImpl
import com.liangsan.keyloler.data.repository.LoginRepositoryImpl
import com.liangsan.keyloler.data.repository.ProfileRepositoryImpl
import com.liangsan.keyloler.data.repository.SearchHistoryRepositoryImpl
import com.liangsan.keyloler.data.repository.SettingsRepositoryImpl
import com.liangsan.keyloler.data.repository.ThreadsRepositoryImpl
import com.liangsan.keyloler.data.repository.UserRepositoryImpl
import com.liangsan.keyloler.domain.repository.CookiesRepository
import com.liangsan.keyloler.domain.repository.ForumCategoryRepository
import com.liangsan.keyloler.domain.repository.IndexRepository
import com.liangsan.keyloler.domain.repository.LoginRepository
import com.liangsan.keyloler.domain.repository.ProfileRepository
import com.liangsan.keyloler.domain.repository.SearchHistoryRepository
import com.liangsan.keyloler.domain.repository.SettingsRepository
import com.liangsan.keyloler.domain.repository.ThreadsRepository
import com.liangsan.keyloler.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.plugins.cookies.CookiesStorage
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

    single<HttpClient> {
        ktorHttpClient(get())
    }

    single<CookiesStorage> {
        PersistentCookiesStorage(get())
    }

    singleOf(::KeylolerService)

    singleOf(::ForumCategoryRepositoryImpl).bind<ForumCategoryRepository>()

    singleOf(::SearchHistoryRepositoryImpl).bind<SearchHistoryRepository>()

    singleOf(::LoginRepositoryImpl).bind<LoginRepository>()

    singleOf(::UserRepositoryImpl).bind<UserRepository>()

    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()

    singleOf(::CookiesRepositoryImpl).bind<CookiesRepository>()

    singleOf(::IndexRepositoryImpl).bind<IndexRepository>()

    singleOf(::ThreadsRepositoryImpl).bind<ThreadsRepository>()

    singleOf(::SettingsRepositoryImpl).bind<SettingsRepository>()
}