package com.liangsan.keyloler.presentation.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.liangsan.keyloler.presentation.login.LoginScreen
import com.liangsan.keyloler.presentation.login.navigation.LoginDestination
import com.liangsan.keyloler.presentation.search_index.index.IndexScreen
import com.liangsan.keyloler.presentation.search_index.navigation.SearchIndexDestination
import com.liangsan.keyloler.presentation.search_index.search.SearchScreen
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            NavHost(
                modifier = modifier,
                navController = navHostController,
                startDestination = TopLevelDestination.Home
            ) {
                composable<TopLevelDestination.Home> {

                }

                navigation<TopLevelDestination.SearchIndex>(startDestination = SearchIndexDestination.Index) {
                    composable<SearchIndexDestination.Index> {
                        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                            IndexScreen(
                                onSearchClick = {
                                    navHostController.navigate(SearchIndexDestination.Search())
                                },
                                onForumClick = {

                                }
                            )
                        }
                    }
                    composable<SearchIndexDestination.Search> {
                        CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                            SearchScreen(onNavigateUp = navHostController::navigateUp)
                        }
                    }
                }

                composable<TopLevelDestination.Profile> {

                }

                composable<LoginDestination.Login> {
                    LoginScreen(onNavigateUp = navHostController::navigateUp)
                }
            }
        }
    }
}