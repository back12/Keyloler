package com.liangsan.keyloler.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.liangsan.keyloler.presentation.main.component.BottomNavBar
import com.liangsan.keyloler.presentation.main.navigation.MainNavHost
import com.liangsan.keyloler.presentation.utils.KeylolerDestination
import com.liangsan.keyloler.presentation.utils.LocalSnackbarScope
import com.liangsan.keyloler.presentation.utils.SnackbarScope
import com.liangsan.keyloler.presentation.utils.Zero
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.conditional
import com.liangsan.keyloler.presentation.utils.isTopLevelDestinationInHierarchy
import com.liangsan.keyloler.presentation.utils.onTopBarNavigate

@Composable
fun KeylolerApp() {
    val snackbarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }
    val navHostController = rememberNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()

    val showBottomBar by remember {
        derivedStateOf {
            currentBackStackEntry?.toRoute<KeylolerDestination>()?.showBottomNav == true
        }
    }

    val scope = rememberCoroutineScope()
    val snackbarScope = remember { SnackbarScope(snackbarHostState, scope) }

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    SwipeToDismissBox(
                        state = rememberSwipeToDismissBoxState(
                            confirmValueChange = {
                                data.dismiss(); true
                            }
                        ),
                        backgroundContent = {}
                    ) {
                        Snackbar(
                            snackbarData = data,
                        )
                    }
                },
                modifier = Modifier.conditional(showBottomBar) {
                    this.bottomBarPadding()
                }
            )
        },
        contentWindowInsets = WindowInsets.Zero
    ) { innerPadding ->
        CompositionLocalProvider(LocalSnackbarScope provides snackbarScope) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                MainNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navHostController = navHostController
                )
                BottomNavBar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    visible = showBottomBar,
                    isSelected = currentBackStackEntry::isTopLevelDestinationInHierarchy,
                    onNavigate = navHostController::onTopBarNavigate
                )
            }
        }
    }
}