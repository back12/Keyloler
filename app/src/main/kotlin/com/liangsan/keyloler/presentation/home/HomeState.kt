package com.liangsan.keyloler.presentation.home

import androidx.compose.runtime.Stable
import com.liangsan.keyloler.domain.model.Slide
import com.liangsan.keyloler.domain.model.Thread
import pro.respawn.flowmvi.api.MVIState

@Stable
data class HomeState(
    val slides: List<Slide> = emptyList(),
    val currentTab: String? = null,
    val tabs: List<String> = emptyList(),
    val currentThreadList: List<Thread> = emptyList(),
    val loading: Boolean = true
) : MVIState