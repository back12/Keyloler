package com.liangsan.keyloler.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.navigation.compose.rememberNavController
import com.liangsan.keyloler.presentation.theme.KeylolerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState by viewModel.store.subscribe()
            val navHostController = rememberNavController()
            KeylolerTheme(
                darkTheme = appState.isDarkTheme,
                dynamicColor = appState.dynamicColor
            ) {
                KeylolerApp(appState = appState, navHostController = navHostController)
            }
        }
    }
}