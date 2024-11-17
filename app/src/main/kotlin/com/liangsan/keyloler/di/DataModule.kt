package com.liangsan.keyloler.di

import androidx.room.Room
import com.liangsan.keyloler.data.local.KeylolerDatabase
import com.liangsan.keyloler.data.remote.KeylolerService
import com.liangsan.keyloler.data.remote.ktorHttpClient
import com.liangsan.keyloler.data.repository.KeylolRepositoryImpl
import com.liangsan.keyloler.domain.repository.KeylolRepository
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

    singleOf(::KeylolRepositoryImpl).bind<KeylolRepository>()
}