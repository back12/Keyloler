package com.liangsan.keyloler.presentation.utils

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import com.liangsan.keyloler.presentation.main.navigation.TopLevelDestination
import kotlinx.serialization.Serializable
import timber.log.Timber

@Serializable
open class KeylolerDestination {
    open val showBottomNav: Boolean = true
}

fun NavBackStackEntry?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.destination?.hierarchy?.any {
        it isSameWith destination
    } ?: false

fun NavHostController.topLevelNavigate(destination: TopLevelDestination) {
    try {
        if (currentDestination?.isSameWith(destination) == true)
            return
        this.navigate(destination) {
            launchSingleTop = true
            popUpTo(graph.startDestinationId)
        }
    } catch (ignored: IllegalArgumentException) {
        Timber.e(ignored)
    }
}

private infix fun NavDestination?.isSameWith(destination: TopLevelDestination): Boolean {
    return this?.route?.substringBefore("/") == destination::class.qualifiedName
}