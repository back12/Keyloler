package com.liangsan.keyloler.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.liangsan.keyloler.presentation.main.component.BottomNavBar
import com.liangsan.keyloler.presentation.main.navigation.MainNavHost
import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import com.liangsan.keyloler.presentation.utils.Zero
import com.liangsan.keyloler.presentation.utils.isTopLevelDestinationInHierarchy
import com.liangsan.keyloler.presentation.utils.onTopBarNavigate

@Composable
fun KeylolerApp() {
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val navHostController = rememberNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        contentWindowInsets = WindowInsets.Zero
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            MainNavHost(modifier = Modifier.fillMaxSize(), navHostController = navHostController)
            BottomNavBar(
                modifier = Modifier.align(Alignment.BottomCenter),
                visible = currentBackStackEntry?.toRoute<KeylolerDestination>()?.showBottomNav == true,
                isSelected = currentBackStackEntry::isTopLevelDestinationInHierarchy,
                onNavigate = navHostController::onTopBarNavigate
            )
        }
    }
}