package com.liangsan.keyloler.domain.utils

import java.security.MessageDigest

fun hashString(str: String, algorithm: String = "MD5"): String =
    MessageDigest.getInstance(algorithm)
        .digest(str.toByteArray())
        .joinToString(separator = "") { byte -> "%02x".format(byte) }