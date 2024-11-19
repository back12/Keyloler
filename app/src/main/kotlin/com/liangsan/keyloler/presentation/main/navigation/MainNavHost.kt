package com.liangsan.keyloler.presentation.main.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.liangsan.keyloler.presentation.search_index.index.IndexScreen
import com.liangsan.keyloler.presentation.search_index.SearchScreen
import com.liangsan.keyloler.presentation.search_index.navigation.SearchIndexDestination

@Composable
fun MainNavHost(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = TopLevelDestination.Home
    ) {
        composable<TopLevelDestination.Home> {
            SearchScreen()
        }

        navigation<TopLevelDestination.SearchIndex>(startDestination = SearchIndexDestination.Index) {
            composable<SearchIndexDestination.Index> {
                IndexScreen()
            }
            composable<SearchIndexDestination.Search> {
                Box(
                    modifier = Modifier.fillMaxSize().background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = {
                        navHostController.navigateUp()
                    }) {
                        Text("back")
                    }
                }
            }
        }
    }
}