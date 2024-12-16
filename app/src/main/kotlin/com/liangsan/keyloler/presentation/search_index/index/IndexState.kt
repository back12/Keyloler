package com.liangsan.keyloler.presentation.search_index.index

import com.liangsan.keyloler.domain.model.ForumWithCategoryList
import com.liangsan.keyloler.domain.utils.Result

data class IndexState(
    val forumCategoryList: Result<ForumWithCategoryList> = Result.Loading
)
