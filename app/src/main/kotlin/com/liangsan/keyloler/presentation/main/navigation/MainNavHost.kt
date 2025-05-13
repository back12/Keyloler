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
import com.liangsan.keyloler.domain.model.Forum
import com.liangsan.keyloler.presentation.home.HomeScreen
import com.liangsan.keyloler.presentation.login.LoginScreen
import com.liangsan.keyloler.presentation.login.navigation.Login
import com.liangsan.keyloler.presentation.main.AppState
import com.liangsan.keyloler.presentation.my_thread.MyThreadScreen
import com.liangsan.keyloler.presentation.my_thread.navigation.MyThread
import com.liangsan.keyloler.presentation.notice.NoticeScreen
import com.liangsan.keyloler.presentation.notice.navigation.Notice
import com.liangsan.keyloler.presentation.profile.navigation.ProfileDestination
import com.liangsan.keyloler.presentation.profile.profile.ProfileScreen
import com.liangsan.keyloler.presentation.profile.profile_info.ProfileInfoScreen
import com.liangsan.keyloler.presentation.search_index.index.IndexScreen
import com.liangsan.keyloler.presentation.search_index.navigation.SearchIndexDestination
import com.liangsan.keyloler.presentation.search_index.search.SearchScreen
import com.liangsan.keyloler.presentation.thread.ThreadScreen
import com.liangsan.keyloler.presentation.thread.navigation.ViewThread
import com.liangsan.keyloler.presentation.thread_history.ThreadHistoryScreen
import com.liangsan.keyloler.presentation.thread_history.navigation.ThreadHistory
import com.liangsan.keyloler.presentation.thread_list.forum_thread_list.ForumThreadListScreen
import com.liangsan.keyloler.presentation.thread_list.navigation.ForumThreadList
import com.liangsan.keyloler.presentation.utils.LocalNavAnimatedVisibilityScope
import com.liangsan.keyloler.presentation.utils.LocalSharedTransitionScope
import com.liangsan.keyloler.presentation.utils.navigateToTopLevel
import com.liangsan.keyloler.presentation.utils.openThread
import com.liangsan.keyloler.presentation.utils.serializableType
import kotlin.reflect.typeOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MainNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    appState: AppState
) {
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
                    CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                        HomeScreen(onOpenThread = navHostController::openThread)
                    }
                }

                composable<ViewThread> {
                    val route = it.toRoute<ViewThread>()
                    CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                        ThreadScreen(
                            tid = route.tid,
                            title = route.title,
                            onNavigateToProfileInfo = { uid, avatar, nickname ->
                                navHostController.navigate(
                                    ProfileDestination.ProfileInfo(
                                        uid = uid,
                                        avatar = avatar,
                                        nickname = nickname
                                    )
                                )
                            },
                            onNavigateUp = navHostController::navigateUp
                        )
                    }
                }

                composable<TopLevelDestination.SearchIndex> {
                    CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                        IndexScreen(
                            onSearchClick = {
                                navHostController.navigate(SearchIndexDestination.Search())
                            },
                            onForumClick = {
                                navHostController.navigate(ForumThreadList(forum = it))
                            }
                        )
                    }
                }

                composable<SearchIndexDestination.Search> {
                    CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                        SearchScreen(onNavigateUp = navHostController::navigateUp)
                    }
                }

                navigation<TopLevelDestination.Profile>(startDestination = ProfileDestination.Overview) {
                    composable<ProfileDestination.Overview> {
                        ProfileScreen(
                            appState = appState,
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
                            },
                            onNavigateToThreadHistory = {
                                navHostController.navigate(ThreadHistory())
                            },
                            onOpenThread = navHostController::openThread,
                            onNavigateToMyThread = {
                                navHostController.navigate(MyThread())
                            },
                            onNavigateToNotice = {
                                navHostController.navigate(Notice())
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
                            navHostController.navigateToTopLevel(TopLevelDestination.Home)
                        }
                    )
                }

                composable<ForumThreadList>(
                    typeMap = mapOf(typeOf<Forum>() to serializableType<Forum>("forum_type"))
                ) {
                    val forum = it.toRoute<ForumThreadList>().forum
                    CompositionLocalProvider(LocalNavAnimatedVisibilityScope provides this) {
                        ForumThreadListScreen(
                            forum = forum,
                            onOpenThread = navHostController::openThread,
                            onNavigateUp = navHostController::navigateUp
                        )
                    }
                }

                composable<ThreadHistory> {
                    ThreadHistoryScreen(
                        onOpenThread = navHostController::openThread,
                        onNavigateUp = navHostController::navigateUp
                    )
                }

                composable<MyThread> {
                    MyThreadScreen(
                        onOpenThread = navHostController::openThread,
                        onNavigateUp = navHostController::navigateUp
                    )
                }

                composable<Notice> {
                    NoticeScreen(
                        onOpenThread = navHostController::openThread,
                        onNavigateUp = navHostController::navigateUp
                    )
                }
            }
        }
    }
}