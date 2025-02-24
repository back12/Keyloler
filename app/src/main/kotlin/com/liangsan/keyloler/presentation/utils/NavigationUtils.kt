package com.liangsan.keyloler.presentation.utils

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import com.liangsan.keyloler.presentation.main.navigation.TopLevelDestination
import com.liangsan.keyloler.presentation.thread.navigation.Thread
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber

@Serializable
open class KeylolerDestination(
    open val showBottomNav: Boolean = true
)

fun NavBackStackEntry?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.destination?.hierarchy?.any {
        it isSameWith destination
    } ?: false

fun NavHostController.navigateToTopLevel(destination: TopLevelDestination) {
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

fun NavHostController.openThread(tid: String, title: String) {
    navigate(Thread(tid = tid, title = title)) {
        launchSingleTop = true
    }
}

private infix fun NavDestination?.isSameWith(destination: TopLevelDestination): Boolean {
    return this?.route?.substringBefore("/") == destination::class.qualifiedName
}

inline fun <reified T : Any> serializableType(
    name: String,
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override val name: String
        get() = name

    override fun get(bundle: Bundle, key: String) =
        bundle.getString(key)?.let<String, T>(json::decodeFromString)

    override fun parseValue(value: String): T = json.decodeFromString(Uri.decode(value))

    override fun serializeAsValue(value: T): String = Uri.encode(json.encodeToString(value))

    override fun put(bundle: Bundle, key: String, value: T) {
        bundle.putString(key, json.encodeToString(value))
    }
}