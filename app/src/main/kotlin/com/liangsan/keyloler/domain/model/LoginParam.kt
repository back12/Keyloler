package com.liangsan.keyloler.domain.model

data class LoginParam(
    val auth: String,
    val update: String,
    val loginHash: String,
    val formHash: String,
    val idHash: String,
)
