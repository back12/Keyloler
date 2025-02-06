package com.liangsan.keyloler.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.liangsan.keyloler.presentation.utils.ObserveAsEvents
import com.liangsan.keyloler.presentation.utils.SnackbarController
import com.liangsan.keyloler.presentation.utils.Zero
import com.liangsan.keyloler.presentation.utils.bottomBarPadding
import com.liangsan.keyloler.presentation.utils.isTopLevelDestinationInHierarchy
import com.liangsan.keyloler.presentation.utils.topLevelNavigate

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

    ObserveAsEvents(flow = SnackbarController.events) { event ->
        snackbarHostState.currentSnackbarData?.dismiss()

        snackbarHostState.showSnackbar(
            message = event.message,
            actionLabel = event.actionLabel,
            withDismissAction = event.withDismissAction,
            duration = event.duration
        ).also {
            event.onResult?.invoke(it)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                snackbar = { data ->
                    SwipeToDismissBox(
                        modifier = Modifier,
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
                modifier = Modifier
                    .imePadding()
                    .safeDrawingPadding()
                    .bottomBarPadding(showBottomBar)
            )
        },
        contentWindowInsets = WindowInsets.Zero
    ) { innerPadding ->
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
                onNavigate = navHostController::topLevelNavigate
            )
        }
    }
}