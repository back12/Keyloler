package com.liangsan.keyloler.presentation.home

import androidx.compose.runtime.Stable
import com.liangsan.keyloler.domain.model.Index
import com.liangsan.keyloler.domain.utils.Result
import pro.respawn.flowmvi.api.MVIState

@Stable
data class HomeState(
    val index: Result<Index> = Result.Loading,
    val currentTab: String? = null
) : MVIState