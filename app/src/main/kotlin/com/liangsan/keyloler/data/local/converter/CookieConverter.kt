package com.liangsan.keyloler.data.local.converter

import io.ktor.http.Cookie

class CookieConverter : JsonConverter<Cookie> by defaultJsonConverter()