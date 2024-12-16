package com.liangsan.keyloler.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Slide(
    val tid: String,
    val img: String,
    val title: String
)