package com.liangsan.keyloler.presentation.search_index.navigation

import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

sealed class SearchIndexDestination : KeylolerDestination() {

    @Serializable
    class Search : SearchIndexDestination() {
        override val showBottomNav: Boolean = false
    }
}