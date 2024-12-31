package com.liangsan.keyloler.presentation.search_index.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class SearchIndexDestination : KeylolerDestination() {

    @Serializable
    data object Index : SearchIndexDestination()

    @Serializable
    class Search : SearchIndexDestination() {
        override val showBottomNav: Boolean = false
    }
}