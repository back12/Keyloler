package com.liangsan.keyloler.presentation.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.liangsan.keyloler.R
import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import kotlinx.serialization.Serializable

@Serializable
sealed class TopLevelDestination(
    @StringRes val name: Int,
    @DrawableRes val icon: Int
) : KeylolerDestination() {

    @Serializable
    data object Home : TopLevelDestination(R.string.home, R.drawable.rounded_home_24)

    @Serializable
    data object SearchIndex : TopLevelDestination(R.string.search, R.drawable.round_search_24)

    companion object {
        fun toList() = listOf(
            Home,
            SearchIndex,
        )
    }
}