package com.liangsan.keyloler

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.liangsan.keyloler.di.dataModule
import com.liangsan.keyloler.di.viewModelModule
import io.ktor.client.HttpClient
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class KeylolerApplication : Application(), SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@KeylolerApplication)
            // Load modules
            modules(dataModule + viewModelModule)
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        val httpClient: HttpClient = get()
        return ImageLoader.Builder(this)
            .crossfade(true)
            .components {
                add(
                    KtorNetworkFetcherFactory(httpClient = { httpClient })
                )
            }
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(this, 0.1)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("keyloler_image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .apply {
                if (BuildConfig.DEBUG) {
                    logger(DebugLogger())
                }
            }
            .build()
    }
}