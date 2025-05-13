package com.liangsan.keyloler.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.liangsan.keyloler.presentation.theme.KeylolerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = viewModel.store.subscribe()
            KeylolerTheme {
                KeylolerApp(appState = appState.value)
            }
        }
    }
}