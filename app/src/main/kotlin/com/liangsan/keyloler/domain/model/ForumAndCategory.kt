package com.liangsan.keyloler.domain.model

import com.liangsan.keyloler.data.local.entity.ForumCategoryEntity
import com.liangsan.keyloler.data.local.entity.ForumEntity
import com.liangsan.keyloler.data.local.relation.ForumsWithCategory

typealias ForumCategory = ForumCategoryEntity

typealias Forum = ForumEntity

typealias ForumWithCategoryList = List<ForumsWithCategory>
