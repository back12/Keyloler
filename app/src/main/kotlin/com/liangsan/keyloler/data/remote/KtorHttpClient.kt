package com.liangsan.keyloler.data.remote

import com.liangsan.keyloler.BuildConfig
import com.liangsan.keyloler.data.remote.keylol_api.KEYLOL_BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

val ktorHttpClient = HttpClient(CIO) {
    expectSuccess = true

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            Timber.e(exception)
        }
    }

    install(Resources)

    defaultRequest {
        url {
            host = KEYLOL_BASE_URL
            protocol = URLProtocol.HTTPS
        }
    }

    if (BuildConfig.DEBUG) {
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    install(ContentNegotiation) {
        json(
            json = Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
                explicitNulls = false
                encodeDefaults = true
            }
        )
    }
}