package com.liangsan.keyloler.domain.model

data class Index(
    val slides: List<Slide> = emptyList(),
    val threadsList: Map<String, List<Thread>> = emptyMap()
)