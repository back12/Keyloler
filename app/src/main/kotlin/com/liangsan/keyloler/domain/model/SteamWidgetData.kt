package com.liangsan.keyloler.domain.model

data class SteamWidgetData(
    val title: String,
    val titleExt: String,
    val image: String,
    val desc: String,
    val platformImages: List<SteamPlatform>,
    val gamePurchasePrice: String,
    val addToCartButton: String
)
