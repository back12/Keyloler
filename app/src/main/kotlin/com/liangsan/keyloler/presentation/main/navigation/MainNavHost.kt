package com.liangsan.keyloler.presentation.main.navigation

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import com.liangsan.keyloler.presentation.home.HomeScreen
import com.liangsan.keyloler.presentation.login.LoginScreen
import com.liangsan.keyloler.presentation.login.navigation.Login
import com.liangsan.keyloler.presentation.profile.navigation.ProfileDestination
import com.liangsan.keyloler.presentation.profile.profile.ProfileScreen
import com.liangsan.keyloler.presentation.profile.profile_info.ProfileInfoScreen
import com.liangsan.keyloler.presentation.search_index.index.IndexScreen
import com.liangsan.keyloler.presentation.search_index.navigation.SearchIndexDestination
import com.liangsan.keyloler.presentation.search_index.search.SearchScreen
import com.liangsan.keyloler.presentation.thread.ThreadScreen
import com.liangsan.keyloler.presentation.thread.navigation.Thread
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.openThread
import com.liangsan.keyloler.presentation.utils.topLevelNavigate

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
                startDestination = TopLevelDestination.Home,
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            ) {
                composable<TopLevelDestination.Home> {
                    HomeScreen(onOpenThread = navHostController::openThread)
                }

                composable<Thread> {
                    val route = it.toRoute<Thread>()
                    ThreadScreen(
                        tid = route.tid,
                        title = route.title,
                        onNavigateUp = navHostController::navigateUp
                    )
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

                navigation<TopLevelDestination.Profile>(startDestination = ProfileDestination.Overview) {
                    composable<ProfileDestination.Overview> {
                        ProfileScreen(
                            onNavigateToLogin = {
                                navHostController.navigate(Login())
                            },
                            onNavigateToProfileInfo = { uid, avatar, nickname ->
                                navHostController.navigate(
                                    ProfileDestination.ProfileInfo(
                                        uid = uid,
                                        avatar = avatar,
                                        nickname = nickname
                                    )
                                )
                            }
                        )
                    }
                    composable<ProfileDestination.ProfileInfo> {
                        val args = it.toRoute<ProfileDestination.ProfileInfo>()
                        ProfileInfoScreen(
                            uid = args.uid,
                            avatar = args.avatar,
                            nickname = args.nickname,
                            onNavigateUp = navHostController::navigateUp
                        )
                    }
                }

                composable<Login> {
                    LoginScreen(
                        onNavigateUp = navHostController::navigateUp,
                        onNavigateToHome = {
                            navHostController.topLevelNavigate(TopLevelDestination.Home)
                        }
                    )
                }
            }
        }
    }
}