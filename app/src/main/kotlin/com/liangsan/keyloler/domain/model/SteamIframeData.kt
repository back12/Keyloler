package com.liangsan.keyloler.domain.model

data class SteamIframeData(
    val title: String,
    val titleExt: String,
    val image: String,
    val desc: String,
    val gamePurchasePrice: String?,
    val addToCartButton: String,
    val gameUrl: String
)
