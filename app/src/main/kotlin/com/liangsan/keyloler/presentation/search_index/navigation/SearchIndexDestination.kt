package com.liangsan.keyloler.presentation.search_index.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class SearchIndexDestination : KeylolerDestination() {

    @Serializable
    data object Index : SearchIndexDestination()

    @Serializable
    data class Search(
        override val showBottomNav: Boolean = false
    ) : SearchIndexDestination()
}