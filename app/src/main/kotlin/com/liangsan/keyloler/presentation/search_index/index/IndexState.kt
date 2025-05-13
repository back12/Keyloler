package com.liangsan.keyloler.presentation.search_index.index

import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import pro.respawn.flowmvi.api.MVIState

data class IndexState(
    val forumCategoryList: ForumWithCategoryList = emptyList()
) : MVIState
